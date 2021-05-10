package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.moaapps.valiant.BaseActivity
import com.moaapps.valiant.R
import com.moaapps.valiant.pojo.Question
import com.moaapps.valiant.ui.adapters.ExerciseResultAdapter
import com.moaapps.valiant.utils.InternetChecker.isConnected
import kotlinx.android.synthetic.main.activity_exercise_result.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExerciseResultActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context, list:ArrayList<Question>, id:Int, isHistory: Boolean = false) {
            val starter = Intent(context, ExerciseResultActivity::class.java)
                .putExtra("results", list)
                .putExtra("id", id)
                .putExtra("history", isHistory)
            context.startActivity(starter)
        }
    }

    private var isHistory = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_result)
        done.setOnClickListener { MainActivity.start(this) }
        isHistory = intent.getBooleanExtra("history", false)
        val questions = intent.getSerializableExtra("results") as ArrayList<Question>
        val id = intent.getIntExtra("id", 0)
        name.text = getName(id)


        val map = HashMap<String, Any>()
        map["data"] = questions
        val user = FirebaseAuth.getInstance().currentUser
        done.isEnabled = false
        if (isConnected(this) && user != null && !isHistory){

            FirebaseFirestore.getInstance()
                .collection("exercises")
                .document(getName(id))
                .collection(user.uid)
                .document("test_${Calendar.getInstance().timeInMillis}")
                .set(map)
                .addOnCompleteListener {
                    progressBar3.visibility = View.GONE
                    done.isEnabled = true
                    if (it.isSuccessful){
                        initRecycler(questions)
                    }else{
                        it.exception?.printStackTrace()
                        snackBar("Error submitting your data")
                    }
                }
        }else{
            done.isEnabled = true
            progressBar3.visibility = View.GONE
            initRecycler(questions)
        }

        if (isHistory){
            done.visibility = View.GONE
            back.visibility = View.VISIBLE
            back.setOnClickListener { finish() }
        }


    }


    private fun getName(id:Int):String{
        return when(id) {
            0 -> "DE catastrophizing"
            1 -> "Exposure Therapy"
            2 -> "Challenging Negative Thoughts"
            3 -> "5-4-3-2-1 Coping Technique"
            else -> ""
        }
    }


    private fun initRecycler(questions: ArrayList<Question>) {
        questions_rv.layoutManager = LinearLayoutManager(this)
        questions_rv.adapter = ExerciseResultAdapter(questions)
    }

    override fun onBackPressed() {
        if (isHistory){
            super.onBackPressed()
        }
    }
}
package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.moaapps.valiant.BaseActivity
import com.moaapps.valiant.R
import com.moaapps.valiant.pojo.Question
import com.moaapps.valiant.pojo.QuestionsList
import com.moaapps.valiant.ui.adapters.ExerciseHistoryAdapter
import kotlinx.android.synthetic.main.activity_exercise_history.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExerciseHistoryActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context, id:Int) {
            val starter = Intent(context, ExerciseHistoryActivity::class.java)
                .putExtra("id", id)
            context.startActivity(starter)
            
        }
        private const val TAG = "ExerciseHistoryActivity"
    }
    private val user = FirebaseAuth.getInstance().currentUser
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_history)
        id = intent.getIntExtra("id",0)
        back.setOnClickListener { finish() }

        name.text = getName(id)

        FirebaseFirestore.getInstance()
            .collection("exercises")
            .document(getName(id))
            .collection(user?.uid!!)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val queries = it.result
                    if (queries?.isEmpty!!){
                        progressBar4.visibility = View.GONE
                        no_history.visibility = View.VISIBLE
                    }else{
                        val ids = ArrayList<String>()
                        for (query in queries){
                            ids.add(query.id)
                        }
                        progressBar4.visibility = View.GONE
                        exercise_history_rv.layoutManager = LinearLayoutManager(this)
                        val adapter = ExerciseHistoryAdapter(ids)
                        adapter.setOnClickListener(object :ExerciseHistoryAdapter.OnItemClickListener{
                            override fun onClick(id: String) {
                                getExercise(id)
                            }
                        })
                        exercise_history_rv.adapter = adapter
                    }
                }else{
                    progressBar4.visibility = View.GONE
                    it.exception?.printStackTrace()
                    snackBar("Error getting exercise data")
                }
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

    fun getExercise(documentId:String){
        showProgressBar()
        FirebaseFirestore.getInstance()
            .collection("exercises")
            .document(getName(id))
            .collection(user?.uid!!)
            .document(documentId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    hideProgressBar()
//                    val data = it.result?.get("data") as ArrayList<HashMap<String,Any>>
                    val questions = it.result?.toObject(QuestionsList::class.java)?.data

                    Log.d(TAG, "getExercise: $questions")
                    ExerciseResultActivity.start(this, questions!!,id, true)
                }else{
                    hideProgressBar()
                    it.exception?.printStackTrace()
                    snackBar("Couldn't get exercise history")
                }
            }
    }
}
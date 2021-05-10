package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.facebook.login.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.moaapps.valiant.BaseActivity
import com.moaapps.valiant.R
import kotlinx.android.synthetic.main.activity_anxiety_result.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class AnxietyResultActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context, score:Int) {
            val starter = Intent(context, AnxietyResultActivity::class.java)
                .putExtra("result", score)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(starter)
        }
    }
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anxiety_result)
        score = intent.getIntExtra("result",0)
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null){
            getResult()
        }else{
            login.visibility = View.GONE
            done.isEnabled = false
            val map = HashMap<String, Any>()
            map["score"] = score
            map["time"] = Calendar.getInstance().timeInMillis
            FirebaseFirestore.getInstance()
                .collection("assesAnxietyTest")
                .document(user.uid)
                .collection("tests")
                .document()
                .set(map)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        done.isEnabled = true
                    }else{
                        snackBar("Error submitting Data")
                        it.exception?.printStackTrace()
                    }
                    getResult()
                }

        }

        done.setOnClickListener { MainActivity.start(this) }
        login.setOnClickListener { LoginActivity.start(this)}
    }

    private fun getResult() {
        loading_layout.visibility = View.GONE

        result_text.text =  when(score){
            in 0..4 -> "Mild Anxiety"
            in 5..9 -> "Moderate Anxiety"
            in 10..14 -> "Moderately Severe Anxiety"
            in 15..21 -> "Severe Anxiety"
            else -> "Couldn't get results"
        }
        GlobalScope.launch {
            delay(500)
            for (i in 0..score){
                runOnUiThread { result_progress_bar.progress = i.toFloat()  }
                delay(30)
            }
        }
    }



}
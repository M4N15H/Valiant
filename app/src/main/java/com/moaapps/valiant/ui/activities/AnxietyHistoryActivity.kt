package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.moaapps.valiant.BaseActivity
import com.moaapps.valiant.R
import com.moaapps.valiant.ui.adapters.AnxietyTestAdapter
import kotlinx.android.synthetic.main.activity_anxiety_history.*

class AnxietyHistoryActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AnxietyHistoryActivity::class.java)
            context.startActivity(starter)
        }
        private const val TAG = "AnxietyHistoryActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anxiety_history)
        showProgressBar()
        val user = FirebaseAuth.getInstance().currentUser
        FirebaseFirestore.getInstance()
            .collection("assesAnxietyTest").document(user?.uid!!)
            .collection("tests")
            .get().addOnCompleteListener {
                if (it.isSuccessful){
                    val scores = it.result
                    if (scores != null) {
                        val scoresList = ArrayList<Map<String, Any>>()
                        for (document in scores) {
                            val score = document.data
                            scoresList.add(score)
                        }
                        Log.d(TAG, "onCreate: ${scoresList.size}")
                        rv_anxiety_test.layoutManager = LinearLayoutManager(this)
                        rv_anxiety_test.adapter = AnxietyTestAdapter(scoresList)
                        hideProgressBar()
                    }else{
                        hideProgressBar()
                        snackBar("Error getting scores")
                    }
                }else{
                    hideProgressBar()
                    it.exception?.printStackTrace()
                    snackBar("Error getting scores")
                }
            }

        back.setOnClickListener { finish() }
    }
}
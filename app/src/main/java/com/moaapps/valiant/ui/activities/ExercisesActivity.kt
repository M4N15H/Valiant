package com.moaapps.valiant.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.moaapps.valiant.R
import kotlinx.android.synthetic.main.activity_exercises.*

class ExercisesActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context, isHistory:Boolean = false) {
            val starter = Intent(context, ExercisesActivity::class.java)
                .putExtra("history", isHistory)
            context.startActivity(starter)
        }
    }

    private  var isHistory = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)
        back.setOnClickListener { finish() }
        isHistory = intent.getBooleanExtra("history", false)
        de_catastrophizing.setOnClickListener {
            if (isHistory){
                ExerciseHistoryActivity.start(this, 0)
            }else{
                ExerciseTakingActivity.start(this,0,"De Catastrophizing")
            }
        }
        
        breathing_exercise.setOnClickListener { BreathingExerciseActivity.start(this) }

        exposure_therapy.setOnClickListener {
            if (isHistory){
                ExerciseHistoryActivity.start(this, 1)
            }else{
                ExerciseTakingActivity.start(this, 1, "Exposure Therapy")
            }
        }

        challenging_negative_thoughts.setOnClickListener {
            if (isHistory){
                ExerciseHistoryActivity.start(this, 2)
            }else{

                ExerciseTakingActivity.start(this,2, "Challenging Negative Thoughts")
            }
        }

        coping_technique.setOnClickListener {
            if (isHistory){
                ExerciseHistoryActivity.start(this, 3)
            }else{
                AlertDialog.Builder(this)
                    .setTitle("Attention")
                    .setMessage("Before starting this exercise, pay attention to your breathing. Slow, deep, long breaths can help you maintain a sense of calm or help you return to a calmer state. Once you find your breath, go through the following steps to help ground yourself:")
                    .setPositiveButton("OK"
                    ) { _, _ -> ExerciseTakingActivity.start(this,3,"5-4-3-2-1 Coping Technique") }
                    .show()
            }

        }

        if (isHistory){
            breathing_exercise.visibility = View.GONE
            breathing_line.visibility = View.GONE
            name.text = "Exercises History"
        }
    }
}
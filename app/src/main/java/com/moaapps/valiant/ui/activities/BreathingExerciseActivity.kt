package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.moaapps.valiant.R
import kotlinx.android.synthetic.main.activity_breathing_exercise.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BreathingExerciseActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, BreathingExerciseActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breathing_exercise)
        back.setOnClickListener { finish() }

        YoYo.with(Techniques.ZoomOut).duration(1).playOn(lung)

        inhale()
    }

    private fun inhale(){
        YoYo.with(Techniques.ZoomInDown).duration(4000).playOn(lung)
        instructions.text = "Inhale"
        GlobalScope.launch {
            for (i in 1..4){
                runOnUiThread { count.text = "$i" }
                delay(1000)
            }

            runOnUiThread { hold() }
        }
    }

    private fun hold(){
        instructions.text = "Hold"
        GlobalScope.launch {
            for (i in 1..7){
                runOnUiThread { count.text = "$i" }
                delay(1000)
            }

            runOnUiThread { exhale() }
        }
    }

    private fun exhale(){
        YoYo.with(Techniques.ZoomOut).duration(10000).playOn(lung)
        instructions.text = "Exhale"
        GlobalScope.launch {
            for (i in 1..8){
                runOnUiThread { count.text = "$i" }
                delay(1000)
            }

        }
    }
}
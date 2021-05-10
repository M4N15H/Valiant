package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.moaapps.valiant.R
import kotlinx.android.synthetic.main.activity_asses_anxiety.*

class AssesAnxietyActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AssesAnxietyActivity::class.java)
            context.startActivity(starter)
        }
        private const val TAG = "AssesAnxietyActivity"

    }
    private val questions = ArrayList<String>()
    private var score = 0
    private var index = 0
    private val transitionDuration:Long = 700
    private val transitionOutDuration:Long = 300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asses_anxiety)

        questions.add("Feeling nervous, anxious or on edge")
        questions.add("Not being able to stop or control worrying")
        questions.add("Worrying too much about different things")
        questions.add("Trouble relaxing")
        questions.add("Being so restless that it's hard to sit still")
        questions.add("Becoming easily annoyed or irritable")
        questions.add("Feeling afraid, as if something awful might happen")

        back.setOnClickListener { finish() }
        not_at_all.setOnClickListener { transitionOut() }
        several_days.setOnClickListener {
            score++
            transitionOut()
        }

        half_the_day.setOnClickListener {
            score+=2
            transitionOut()
        }

        every_day.setOnClickListener {
            score+=3
            transitionOut()
        }

        resetViews()
//        transitionIn()
    }

    private fun transitionIn(){
        setQuestion()
        YoYo.with(Techniques.SlideInDown).duration(transitionDuration).playOn(questionCardView)
        YoYo.with(Techniques.SlideInLeft).duration(transitionDuration).playOn(not_at_all)
        YoYo.with(Techniques.SlideInLeft).duration(transitionDuration+100).playOn(several_days)
        YoYo.with(Techniques.SlideInLeft).duration(transitionDuration+200).playOn(half_the_day)
        YoYo.with(Techniques.SlideInLeft).duration(transitionDuration+300).playOn(every_day)
    }

    private fun setQuestion() {
        if (index < questions.size){
            question.text = questions[index]
            index++
        }else{
            AnxietyResultActivity.start(this, score)
        }

    }

    private fun transitionOut(){
        YoYo.with(Techniques.SlideOutDown).duration(transitionOutDuration).playOn(questionCardView)
        YoYo.with(Techniques.SlideOutLeft).duration(transitionOutDuration).playOn(not_at_all)
        YoYo.with(Techniques.SlideOutLeft).duration(transitionOutDuration+100).playOn(several_days)
        YoYo.with(Techniques.SlideOutLeft).duration(transitionOutDuration+200).playOn(half_the_day)
        YoYo.with(Techniques.SlideOutLeft).onEnd { transitionIn() }.duration(transitionOutDuration+300).playOn(every_day)
    }

    private fun resetViews(){
        YoYo.with(Techniques.SlideOutDown).duration(1).playOn(questionCardView)
        YoYo.with(Techniques.SlideOutLeft).duration(1).playOn(not_at_all)
        YoYo.with(Techniques.SlideOutLeft).duration(1).playOn(several_days)
        YoYo.with(Techniques.SlideOutLeft).duration(1).playOn(half_the_day)
        YoYo.with(Techniques.SlideOutLeft)
            .onEnd {
                transitionIn()
            }.duration(1).playOn(every_day)
    }
}
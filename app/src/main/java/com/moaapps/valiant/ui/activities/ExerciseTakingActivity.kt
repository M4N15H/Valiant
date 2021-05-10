
package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.moaapps.valiant.BaseActivity
import com.moaapps.valiant.R
import com.moaapps.valiant.pojo.Question
import kotlinx.android.synthetic.main.activity_exercise_taking.*
import kotlinx.android.synthetic.main.layout_instructions.view.*

class ExerciseTakingActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context, id: Int, title: String) {
            val starter = Intent(context, ExerciseTakingActivity::class.java)
                .putExtra("id", id).putExtra("title", title)
            context.startActivity(starter)
        }
        private const val TAG = "ExerciseTakingActivity"
    }
    private var index = 0
    private var imageIndex = 0
    private val images = ArrayList<Int>()
    private val questionsList = ArrayList<Question>()
    private val transitionDuration:Long = 700
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_taking)
        back.setOnClickListener { finish() }
        name.text = intent.getStringExtra("title")
        id = intent.getIntExtra("id", 0)

        images.add(R.drawable.ic_exercise_image_one)
        images.add(R.drawable.ic_exercise_image_two)
        images.add(R.drawable.ic_exercise_image_three)
        images.add(R.drawable.ic_exercise_image_four)
        questionsList.addAll(getQuestions())
        resetViews()
        nextQuestion()

        next.setOnClickListener {
            val q = questionsList[index - 1]
            val answer = answer.text.toString()
            if (answer.isNotEmpty()){
                q.answer = answer

//                    nextQuestion()
            }else{
                snackBar("Enter your answer first!")
            }
            if (index < questionsList.size){
                transitionOut()
            }else{
                hideKeyboard()
                ExerciseResultActivity.start(this, questionsList, id)
            }
        }

        answerContainer.setOnClickListener {
            answer.requestFocus()
            showKeyboard()
        }

        if (id == 1){
            info.visibility = View.VISIBLE
        }

        info.setOnClickListener { showInstructions() }

    }

    private fun showKeyboard() {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(container.windowToken, 0)
    }

    private fun showInstructions(){

        val instructions = ArrayList<String>()
        instructions.add("What's the event or situation that caused the thought? For eg. \''I made a mistake at work.\'")
        instructions.add("These are negative and irrational. For eg. \'I\'m probably going to be fired. I always miss up. This is it. I am no good at this job.\'")
        instructions.add("A rational thought about the situation. For eg. \'Missed up, but mistakes happen. I\'m going to work through this, like I always do.\'")
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_instructions, null)
        view.instructions.text = instructions[index-1]
        dialog.setContentView(view)
        dialog.show()


    }

    private fun nextQuestion(){
        question.text = questionsList[index].question
        answer.hint = questionsList[index].hint
        answer.setText("")
        image.setImageResource(images[imageIndex])
        index++
        imageIndex++
        if (imageIndex == images.size){
            imageIndex = 0
        }
        transitionIn()

    }

    private fun transitionIn(){
        YoYo.with(Techniques.ZoomInDown).duration(transitionDuration).playOn(image)
        YoYo.with(Techniques.SlideInDown).duration(transitionDuration).playOn(question)
        YoYo.with(Techniques.FadeIn).duration(transitionDuration).playOn(answerContainer)
    }

    private fun transitionOut(){
        YoYo.with(Techniques.ZoomOutUp).duration(transitionDuration).playOn(image)
        YoYo.with(Techniques.SlideOutDown).duration(transitionDuration).playOn(question)
        YoYo.with(Techniques.FadeOut).duration(transitionDuration)
            .onEnd { nextQuestion() }
            .playOn(answerContainer)
    }

    private fun resetViews(){
        YoYo.with(Techniques.ZoomOutUp).duration(1).playOn(image)
        YoYo.with(Techniques.SlideOutDown).duration(1).playOn(question)
        YoYo.with(Techniques.FadeOut).duration(1).playOn(answerContainer)
    }





    private fun getQuestions():ArrayList<Question> {

        val deQuestions = ArrayList<Question>()
        deQuestions.add(Question("What are you worried about?"))
        deQuestions.add(
            Question(
                "How likely is that your worry will come true?",
                hint = "give examples of past experiences, or other evidence, to support your answer"
            )
        )
        deQuestions.add(Question("If your worry does come true, what's the worst that could happen?"))
        deQuestions.add(Question("If your worry does come true, what's most likely to happen?"))
        deQuestions.add(
            Question(
                "If your worry comes true, what are the chances you'll be ok in one week? [Percentage]",
                hint = "In one week ?"
            )
        )
        deQuestions.add(
            Question(
                "If your worry comes true, what are the chances you'll be ok in one month? [Percentage]",
                hint = "In one month ?"
            )
        )
        deQuestions.add(
            Question(
                "If your worry comes true, what are the chances you'll be ok in one year? [Percentage]",
                hint = "In one year ?"
            )
        )


        val exposureTherapy = ArrayList<Question>()
        exposureTherapy.add(Question("Trigger"))
        exposureTherapy.add(Question("Automatic Thought"))
        exposureTherapy.add(Question("New Thought"))

        val challengingNegativeThoughts = ArrayList<Question>()
        challengingNegativeThoughts.add(Question("What is my thought that is bothering me?"))
        challengingNegativeThoughts.add(Question("Is there substantial evidence for my thought?"))
        challengingNegativeThoughts.add(Question("Is there evidence contrary to my thought?"))
        challengingNegativeThoughts.add(Question("Am I attempting to interpret this situation without all the evidence?"))
        challengingNegativeThoughts.add(Question("What would a friend think about this situation?"))
        challengingNegativeThoughts.add(Question("If i look at the situation positively, how is it different?"))
        challengingNegativeThoughts.add(Question("Will this matter a year from now?"))
        challengingNegativeThoughts.add(Question("What about five years from now?"))


        val copingTechniques = ArrayList<Question>()
        copingTechniques.add(Question("Acknowledge FIVE things you see around you. It could be a pen, a spot on the ceiling, anything in your surroundings."))
        copingTechniques.add(Question("Acknowledge FOUR things you can touch around you. It could be your hair, a pillow, or the ground under your feet."))
        copingTechniques.add(Question("Acknowledge THREE things you hear. This could be any external sound. If you can hear your belly rumbling that counts! Focus on things you can hear outside of your body."))
        copingTechniques.add(Question("Acknowledge TWO things you can smell. Maybe you are in your office and smell pencil, or maybe you are in your bedroom and smell a pillow. If you need to take a brief walk to find a scent you could smell soap in your bathroom, or nature outside."))
        copingTechniques.add(Question("Acknowledge ONE thing you can taste. What does the inside of your mouth taste like gum, coffee, or the sandwich from lunch?"))

        return when(id){
            0 -> deQuestions
            1 -> exposureTherapy
            2 -> challengingNegativeThoughts
            3 -> copingTechniques
            else -> deQuestions
        }
    }
}
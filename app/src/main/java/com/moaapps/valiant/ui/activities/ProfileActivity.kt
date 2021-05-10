package com.moaapps.valiant.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.moaapps.valiant.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ProfileActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val user = FirebaseAuth.getInstance().currentUser
        back.setOnClickListener { finish() }
        Picasso.get().load(user?.photoUrl).into(profile_image)
        username.text = user?.displayName
        my_anxiety_scores.setOnClickListener {
            AnxietyHistoryActivity.start(this)
        }

        my_exercises.setOnClickListener {
            ExercisesActivity.start(this, true)
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            MainActivity.start(this)
        }
    }
}
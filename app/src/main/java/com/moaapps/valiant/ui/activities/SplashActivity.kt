package com.moaapps.valiant.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moaapps.valiant.R
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Timer().schedule(2000){
            runOnUiThread {
                LoginActivity.start(this@SplashActivity)
                finish()
            }
        }
    }
}
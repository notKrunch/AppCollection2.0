package com.example.expensetrackerapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // 2 seconds delay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Start main activity after splash delay
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish() // Close splash activity
        }, SPLASH_DELAY)
    }
}

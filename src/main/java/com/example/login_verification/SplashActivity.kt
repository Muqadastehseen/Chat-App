package com.example.login_verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.login_verification.Lets_Started
import com.example.login_verification.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()



        Handler().postDelayed({
            val intent = Intent(this,Lets_Started::class.java)
            startActivity(intent)
            finish()




        },4000)


    }
}
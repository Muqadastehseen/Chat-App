package com.example.login_verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Lets_Started : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lets_started)
        supportActionBar?.hide()

        val myButton:Button = findViewById(R.id.btn1)
        myButton.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()

        }

  }
}
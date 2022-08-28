package com.example.projectfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splasScreen()

    }

        private fun splasScreen() {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, Home_Activity::class.java)
                startActivity(intent)
                finish()
            },5000)
    }
}
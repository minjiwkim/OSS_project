package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        Handler().postDelayed({
            val intent = Intent(
                this@FirstActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish() // FirstActivity 종료
        }, 2000) // 2초 후 전환
    }
}
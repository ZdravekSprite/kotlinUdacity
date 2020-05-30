package com.example.android.aboutme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addNickname(it)
        }
    }

    private fun addNickname(view: View?) {
        val editText = findViewById<EditText>(R.id.nickname_edit)
        val nicknameTextView = findViewById<TextView>(R.id.nickname_text)

    }
}
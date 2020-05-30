package com.example.android.colormyviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> =
            listOf(box_one_text, box_two_text, box_three_text,
                box_four_text, box_five_text)

        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }
}
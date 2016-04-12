package com.example.santiago.kotlintests

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.santiago.kotlintests.char_counter.CharCounterActivity

/**
 * Created by santiago on 11/04/16.
 */
class HomeActivity : Activity() {

    lateinit var programLetterCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        programLetterCounter = findViewById(R.id.activity_home_letter_counter) as TextView

        programLetterCounter.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent: Intent = Intent(this@HomeActivity, CharCounterActivity::class.java)
                startActivity(intent)
            }
        })
    }

}
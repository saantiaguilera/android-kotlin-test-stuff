package com.example.santiago.kotlintests

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.santiago.kotlintests.char_counter.CharCounterActivity
import com.example.santiago.kotlintests.queue_list.QueueActivity

/**
 * Created by santiago on 11/04/16.
 */
class HomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        findViewById(R.id.activity_home_queue).setOnClickListener { v -> startAnApp(QueueActivity::class.java) }
        findViewById(R.id.activity_home_letter_counter).setOnClickListener { v -> startAnApp(CharCounterActivity::class.java) }
    }

    fun startAnApp(aClass: Class<*>) {
        startActivity(Intent(this@HomeActivity, aClass))
    }

}
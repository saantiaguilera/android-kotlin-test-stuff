package com.example.santiago.kotlintests.queue_list

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.santiago.kotlintests.R
import java.util.*

/**
 * Created by santiago on 11/04/16.
 */
class QueueActivity : Activity() {

    val queue: Queue<String> = ArrayDeque<String>()

    lateinit var currentMessage: TextView
    lateinit var input: EditText
    lateinit var submit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_queue)

        currentMessage = findViewById(R.id.activity_queue_message) as TextView
        input = findViewById(R.id.activity_queue_edittext) as EditText
        submit = findViewById(R.id.activity_queue_send) as ImageView

        submit.setOnClickListener { v -> submit(input.text.toString()) }
        currentMessage.setOnClickListener { v -> if(!queue.isEmpty()) deque() }
    }

    fun deque() {
        currentMessage.text = queue.poll()

        queue.add(currentMessage.text.toString())
    }

    fun submit(text: String?) {
        currentMessage.text = text

        queue.add(text)

        input.setText("")
    }

}
package com.example.santiago.kotlintests.queue_list

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.example.santiago.my_java_libs.event.EventManager
import com.example.santiago.kotlintests.R
import com.example.santiago.kotlintests.queue_list.event.NewTaskEvent
import com.example.santiago.kotlintests.queue_list.recycler.controller.QueueListController
import com.example.santiago.kotlintests.queue_list.recycler.view.QueueListView

/**
 * Created by santiago on 11/04/16.
 */
class QueueActivity : Activity() {

    val eventManager = EventManager(this)

    val queueListController = QueueListController(this)

    lateinit var input: EditText
    lateinit var submit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_queue)

        queueListController.attachElement(findViewById(R.id.activity_queue_recyclerview) as QueueListView)
        queueListController.setEventHandlerListener(eventManager)

        input = findViewById(R.id.activity_queue_edittext) as EditText
        submit = findViewById(R.id.activity_queue_send) as ImageView

        submit.setOnClickListener {
            v ->
            eventManager.broadcastEvent(NewTaskEvent(input.text.toString()))
            input.text.clear()
        }
    }

}
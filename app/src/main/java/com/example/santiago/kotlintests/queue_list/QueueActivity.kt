package com.example.santiago.kotlintests.queue_list

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.example.santiago.my_java_libs.event.EventManager
import com.example.santiago.kotlintests.R
import com.example.santiago.kotlintests.queue_list.entity.Task
import com.example.santiago.kotlintests.queue_list.event.NewTaskEvent
import com.example.santiago.kotlintests.queue_list.recycler.controller.QueueListController
import com.example.santiago.kotlintests.queue_list.recycler.view.QueueListView
import com.example.santiago.kotlintests.queue_list.serializers.TaskSerializer
import com.example.santiago.my_java_libs.shared_preferences.JSONSharedPreferences

/**
 * Created by santiago on 11/04/16.
 */
class QueueActivity : Activity() {

    private val JSON_TASK_DOCUMENT = "tasks"
    private val JSON_TASK_LAST_ID = "task_last_id"
    private val JSON_TASK_LIST = "tasks"

    private val eventManager = EventManager(this)

    private val queueListController = QueueListController(this)

    private val sharedPrefs: JSONSharedPreferences by lazy {
        val sp = JSONSharedPreferences(this)
        sp.openDocument(JSON_TASK_DOCUMENT)
        sp
    }

    private val taskSerializer = TaskSerializer()

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
            eventManager.broadcastEvent(NewTaskEvent(Task(nextTaskID(), input.text.toString())))
            input.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()

        queueListController.setTasks(sharedPrefs.get(JSON_TASK_LIST, taskSerializer))
    }

    override fun onStop() {
        super.onStop()

        sharedPrefs.startEditing().put(JSON_TASK_LIST, queueListController.getTasks(), taskSerializer).apply()
    }

    private fun nextTaskID(): Long {
        val nextId = sharedPrefs.getLong(JSON_TASK_LAST_ID, 0L) + 1
        sharedPrefs.startEditing().put(JSON_TASK_LAST_ID, nextId).apply()
        return nextId
    }

}
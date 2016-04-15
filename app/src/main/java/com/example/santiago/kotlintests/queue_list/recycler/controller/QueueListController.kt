package com.example.santiago.kotlintests.queue_list.recycler.controller

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.santiago.kotlintests.queue_list.entity.Task
import com.example.santiago.my_java_libs.controllers.BaseController
import com.example.santiago.my_java_libs.event.EventManager
import com.example.santiago.kotlintests.queue_list.recycler.view.QueueListView
import java.util.*

/**
 * Created by santiago on 13/04/16.
 */
class QueueListController(context: Context) : BaseController<QueueListView>(context) {

    private val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
    private val adapter = QueueListAdapter(context)

    override fun onElementAttached(queueListView: QueueListView?) {
        queueListView?.adapter = adapter.element
        queueListView?.layoutManager = layoutManager
    }

    override fun setEventHandlerListener(eventManager: EventManager?) {
        super.setEventHandlerListener(eventManager)
        adapter.setEventHandlerListener(eventManager)
    }

    fun getTasks(): MutableList<Task> = adapter.queue

    fun setTasks(tasks: MutableList<Task>) = adapter.apply {
        queue.clear()
        queue.addAll(tasks)
        element.notifyDataSetChanged()
    }

}
package com.example.santiago.kotlintests.queue_list.recycler.controller

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import com.example.santiago.kotlintests.queue_list.entity.Task
import com.example.santiago.my_java_libs.controllers.BaseController
import com.example.santiago.my_java_libs.controllers.recycler_stuff.BaseRecyclerAdapter
import com.example.santiago.my_java_libs.event.anotation.EventMethod
import com.example.santiago.kotlintests.queue_list.event.NewTaskEvent
import com.example.santiago.kotlintests.queue_list.recycler.view.TaskView
import java.util.*

/**
 * Created by santiago on 13/04/16.
 */
class QueueListAdapter : BaseController<BaseRecyclerAdapter<TaskView, Task>>, TaskView.TaskCallback {

    var queue: MutableList<Task> = ArrayList()

    constructor(context: Context) : super(context) {
        attachElement(object : BaseRecyclerAdapter<TaskView, Task>(queue) {

            override fun createView(parent: ViewGroup?, viewType: Int): TaskView? = TaskView(getContext())

            override fun bindView(taskView: TaskView?, task: Task?) {
                taskView?.apply {
                    setText(task?.task!!)
                    listener = this@QueueListAdapter
                    setBlur(queue.indexOf(task) != 0)
                }
            }
        })
    }

    override fun onElementAttached(t: BaseRecyclerAdapter<TaskView, Task>?) { }

    override fun onRequeue() = deque()
    override fun onRemove() {
        queue.removeAt(0)

        element.notifyDataSetChanged()
    }

    private fun deque() {
        val text = queue.removeAt(0)
        queue.add(text)

        element.notifyDataSetChanged()
    }

    @EventMethod(NewTaskEvent::class)
    private fun submit(event: NewTaskEvent) {
        queue.add(event.task)

        element.notifyDataSetChanged()
    }

}
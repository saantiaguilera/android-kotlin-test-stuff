package com.example.santiago.kotlintests.queue_list.recycler.controller

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import com.example.santiago.my_java_libs.controllers.BaseController
import com.example.santiago.my_java_libs.controllers.recycler_stuff.BaseRecyclerAdapter
import com.example.santiago.my_java_libs.event.anotation.EventMethod
import com.example.santiago.kotlintests.queue_list.event.NewTaskEvent
import com.example.santiago.kotlintests.queue_list.recycler.view.TaskView
import java.util.*

/**
 * Created by santiago on 13/04/16.
 */
class QueueListAdapter : BaseController<BaseRecyclerAdapter<TaskView, String>>, TaskView.TaskCallback {

    val queue: MutableList<String> = ArrayList()

    constructor(context: Context) : super(context) {
        attachElement(object : BaseRecyclerAdapter<TaskView, String>(queue) {

            override fun createView(parent: ViewGroup?, viewType: Int): TaskView? = TaskView(getContext())

            override fun bindView(textView: TaskView?, string: String?) {
                textView?.apply {
                    text = string
                    textSize = 20f
                    gravity = Gravity.CENTER
                    listener = this@QueueListAdapter

                    //TODO create a data class for the message and compare id!
                    isClickable = (queue.indexOf(string) == queue.size - 1)
                }
            }

        })
    }

    override fun onElementAttached(t: BaseRecyclerAdapter<TaskView, String>?) { }

    override fun onRequeue() = deque()
    override fun onRemove() {
        queue.removeAt(queue.size - 1)

        element.notifyDataSetChanged()
    }

    private fun deque() {
        val text = queue.removeAt(queue.size - 1)
        queue.add(0, text)

        element.notifyDataSetChanged()
    }

    @EventMethod(NewTaskEvent::class)
    private fun submit(event: NewTaskEvent) {
        queue.add(event.task)

        element.notifyDataSetChanged()
    }

}
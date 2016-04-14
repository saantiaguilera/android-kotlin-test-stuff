package com.example.santiago.kotlintests.queue_list.recycler.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import com.example.santiago.kotlintests.R

/**
 * Created by santiago on 13/04/16.
 */
class TaskView : TextView {

    var listener: TaskCallback? = null

    lateinit var startPoint: Pair<Float, Float>

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        minHeight = 150
        gravity = Gravity.CENTER
        textSize = 18f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        fun move() {
            if (event != null) {
                val padding = (startPoint.first - event.x)
                if (padding > 0)
                    setPadding(0, 0, padding.toInt(), 0)
                else setPadding(-padding.toInt(), 0, 0, 0)
            }
        }

        fun finish() {
            when {
                paddingRight > width/2 -> listener?.onRequeue()
                paddingLeft > width/2 -> listener?.onRemove()
            }
            setPadding(0, 0, 0, 0)
        }

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> startPoint = Pair(event?.x?:0f, event?.y?:0f)
            MotionEvent.ACTION_MOVE -> move()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> finish()
        }

        return true
    }

    interface TaskCallback {
        fun onRequeue()
        fun onRemove()
    }

}
package com.example.santiago.kotlintests.queue_list.recycler.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.santiago.kotlintests.R
import jp.wasabeef.blurry.Blurry

/**
 * Created by santiago on 13/04/16.
 */
class TaskView : RelativeLayout {

    private val task by lazy { findViewById(R.id.view_task_text) as TextView }
    private val binImage by lazy { findViewById(R.id.view_task_bin) as ImageView }
    private val requeueImage by lazy { findViewById(R.id.view_task_requeue) as ImageView }

    private var active: Boolean = true

    var listener: TaskCallback? = null

    private lateinit var startPoint: Pair<Float, Float>

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate(context, R.layout.view_task, this)

        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setText(text: String) {
        task.text = text
    }

    fun setBlur(blur: Boolean) {
        if (blur) {
            Blurry.with(context).radius(20)
                    .animate(800)
                    .async()
                    .onto(this);
        } else Blurry.delete(this)

        active = !blur
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(!active)
            return true

        fun move() {
            if (event != null) {
                val padding = (startPoint.first - event.x)

                if (padding > 0) {
                    val params = requeueImage.layoutParams as RelativeLayout.LayoutParams
                    params.width = padding.toInt()
                    requeueImage.layoutParams = params
                } else {
                    val params = binImage.layoutParams as RelativeLayout.LayoutParams
                    params.width = -padding.toInt()
                    binImage.layoutParams = params
                }
            }
        }

        fun finish() {
            when {
                requeueImage.width > width/2 -> listener?.onRequeue()
                binImage.width > width/2 -> listener?.onRemove()
            }

            val binParams = binImage.layoutParams as RelativeLayout.LayoutParams
            binParams.width = 0
            binImage.layoutParams = binParams

            val requeueParams = requeueImage.layoutParams as RelativeLayout.LayoutParams
            requeueParams.width = 0
            requeueImage.layoutParams = requeueParams
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
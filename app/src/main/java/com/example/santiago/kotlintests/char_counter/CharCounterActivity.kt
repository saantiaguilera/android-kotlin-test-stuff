package com.example.santiago.kotlintests.char_counter

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.santiago.kotlintests.R

/**
 * Created by santiago on 11/04/16.
 */
class CharCounterActivity : Activity() {

    var timer = CountDownTimer(4000)

    lateinit var container: LinearLayout
    lateinit var input: EditText
    lateinit var counter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_char_counter)

        container = findViewById(R.id.activity_char_counter_container) as LinearLayout
        input = findViewById(R.id.activity_char_counter_edittext) as EditText
        counter = findViewById(R.id.activity_char_counter_countdown) as TextView

        input.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                timer.apply {
                    cancel()
                    start()
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    fun addToContainer(char: Char, value: Int) {
        container.addView(TextView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50)
            text = "$char = $value"
            textSize = 20f
            gravity = Gravity.CENTER

            setBackgroundColor(getResources().getColor(if(container.childCount % 2 == 0) R.color.black else R.color.white))
            setTextColor(getResources().getColor(if(container.childCount % 2 == 1) R.color.black else R.color.white))
        })
    }

    inner class CountDownTimer(time: Long) : android.os.CountDownTimer(time, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            if(millisUntilFinished - 1000 < 3000) {
                var animation = AnimationUtils.loadAnimation(this@CharCounterActivity, R.anim.grow_n_dissappear);
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationEnd(animation: Animation?) {
                        counter.visibility = View.INVISIBLE
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        counter.visibility = View.VISIBLE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
                counter.text = (millisUntilFinished / 1000).toString()
                counter.startAnimation(animation)
            }
        }

        override fun onFinish() {
            container.removeAllViews()

            input.text.toString()
                    .groupBy { it }
                    .forEach { it -> addToContainer(it.key, it.value.size) }
        }

    }

}
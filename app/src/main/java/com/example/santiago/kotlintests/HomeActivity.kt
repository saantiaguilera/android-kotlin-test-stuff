package com.example.santiago.kotlintests

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

/**
 * Created by santiago on 11/04/16.
 */
class HomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "Just starting :(", Toast.LENGTH_LONG).show()
    }

}
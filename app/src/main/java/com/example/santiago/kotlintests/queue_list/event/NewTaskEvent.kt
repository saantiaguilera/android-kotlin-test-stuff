package com.example.santiago.kotlintests.queue_list.event

import com.example.santiago.my_java_libs.event.Event

/**
 * Created by santiago on 13/04/16.
 */
class NewTaskEvent(val task: String) : Event() {}
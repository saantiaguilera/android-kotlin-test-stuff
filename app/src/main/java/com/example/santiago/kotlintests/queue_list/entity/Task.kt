package com.example.santiago.kotlintests.queue_list.entity

import com.example.santiago.my_java_libs.entity.BaseEntity

/**
 * Created by santiago on 14/04/16.
 */
class Task(id: Long, val task: String) : BaseEntity() {

    init {
        this.id = id
    }

}
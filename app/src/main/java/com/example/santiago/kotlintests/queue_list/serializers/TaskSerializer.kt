package com.example.santiago.kotlintests.queue_list.serializers

import android.content.Context
import com.example.santiago.kotlintests.queue_list.entity.Task
import com.example.santiago.my_java_libs.serializer.BaseJSONSerializer
import com.example.santiago.my_java_libs.shared_preferences.JSONSharedPreferences
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by santiago on 14/04/16.
 */
class TaskSerializer() : BaseJSONSerializer<Task>(), JSONSharedPreferences.JSONSharedPreferencesListSerializer<Task>, JSONSharedPreferences.JSONSharedPreferencesListHidrater<Task> {

    private val JSON_TASK_ID = "id"
    private val JSON_TASK_MESSAGE = "message"

    override fun hidrateListFromSP(string: String?): MutableList<Task>? = hidrate(JSONArray(string))

    override fun serializeListFromSP(tList: MutableList<Task>?): String? = serialize(tList).toString()

    override fun hidrate(jobj: JSONObject?): Task? {
        val id = jobj?.optLong(JSON_TASK_ID)
        val message = jobj?.optString(JSON_TASK_MESSAGE)
        return Task(id!!, message!!)
    }

    override fun serialize(t: Task?): JSONObject? {
        val jobj = JSONObject()
        jobj.put(JSON_TASK_ID, t?.id)
        jobj.put(JSON_TASK_MESSAGE, t?.task)
        return jobj
    }

}
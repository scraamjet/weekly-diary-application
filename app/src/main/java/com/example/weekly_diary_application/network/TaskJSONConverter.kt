package com.example.weekly_diary_application.network

import com.example.weekly_diary_application.data.TaskEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


class TaskJSONConverter {

    fun convertToTaskEntity(taskJSONList: List<TaskJSON>):List<TaskEntity> {
        return taskJSONList.map {
            TaskEntity(
                it.id,
                it.date_start,
                it.date_finish,
                it.name,
                it.description
            )
        }
    }
    fun readJsonToList(inputStream: InputStream): List<TaskJSON>{
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()
        val listType = object : TypeToken<List<TaskJSON>>(){}.type
        return Gson().fromJson(json, listType)
    }
}
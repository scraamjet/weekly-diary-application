package com.example.weekly_diary_application.common

import androidx.room.Room
import com.example.weekly_diary_application.data.TaskDAO
import com.example.weekly_diary_application.data.TaskDatabase

class Application: android.app.Application() {
    lateinit var taskDao: TaskDAO;
    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "task-database"

        ).build()
        taskDao = db.taskDAO
    }
}
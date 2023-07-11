package com.example.weekly_diary_application.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase:RoomDatabase() {
    abstract val taskDAO: TaskDAO

}
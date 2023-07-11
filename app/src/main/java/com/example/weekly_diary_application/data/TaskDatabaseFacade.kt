package com.example.weekly_diary_application.data

import com.example.weekly_diary_application.common.Application
import java.time.*
import java.time.format.DateTimeFormatter

class TaskDatabaseFacade(private val application: Application) {
    suspend fun insertTaskJSON(jsonList: List<TaskEntity>){
        application.taskDao.insertTask(jsonList)
    }
    suspend fun getTasksForDay(time: Long): List<TaskEntity>{
        DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy HH:mm:ss")
        val startOfDay: LocalDateTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate().atTime(LocalTime.MIN);
        val timestamp = startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDate: LocalDateTime = startOfDay.toLocalDate().atTime(LocalTime.MAX)
        val  timestampEnd= endOfDate.atZone(ZoneId.of("UTC")).toInstant().epochSecond
        return application.taskDao.getTasksInTimeInterval(timestamp/1000, timestampEnd)
    }
}
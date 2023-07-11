package com.example.weekly_diary_application.data

import androidx.room.*

@Dao
interface TaskDAO {

    @Query("SELECT * FROM taskEntity ORDER BY :id ASC")
    suspend fun getTaskById(id: Int): TaskEntity

    @Query("SELECT * FROM taskEntity")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM taskEntity WHERE date_start BETWEEN :start AND :end")

    suspend fun getTasksInTimeInterval(start: Long, end: Long): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskList: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

}
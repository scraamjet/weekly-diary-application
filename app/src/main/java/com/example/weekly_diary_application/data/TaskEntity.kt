package com.example.weekly_diary_application.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity (
    @PrimaryKey
    val id:Int,
    val date_start: Long,
    val date_finish: Long,
    val name: String,
    val description: String
    )
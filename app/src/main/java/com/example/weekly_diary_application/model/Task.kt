package com.example.weekly_diary_application.model

import java.sql.Timestamp

data class Task (
    private val id:Int,
    private val date_start: Timestamp,
    private val date_finnish: Timestamp,
    private val name: String,
    private val description: String

)
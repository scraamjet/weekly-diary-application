package com.example.weekly_diary_application.network

import kotlinx.serialization.SerialName

data class TaskJSON (
    @SerialName("id")
    val id:Int,
    @SerialName("date_start")
    val date_start: Long,
    @SerialName("date_finish")
    val date_finish: Long,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String

)
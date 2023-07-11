package com.example.weekly_diary_application.feature_calendar

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    fun convertLongToTimeString(timeInMillis: Long): String {
        val date = Date(timeInMillis)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}
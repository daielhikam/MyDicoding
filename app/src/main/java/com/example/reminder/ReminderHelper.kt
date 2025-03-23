package com.example.reminder

import android.content.Context
import android.util.Log
import androidx.work.*



class ReminderHelper(private val context: Context) {
    fun cancelDailyReminder() {
        Log.d("ReminderHelper", "WorkManager akan dibatalkan")
        WorkManager.getInstance(context).cancelUniqueWork("daily_reminder")
        WorkManager.getInstance(context).cancelAllWork()
    }
}

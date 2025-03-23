package com.example.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.example.mydicoding.MainActivity
import com.example.mydicoding.R

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("ReminderWorker", "WorkManager berjalan, mengirim notifikasi...")
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "event_reminder_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Event Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Ambil data dari WorkManager
        val eventName = inputData.getString("EVENT_NAME") ?: "Event Tanpa Nama"
        val eventTime = inputData.getString("EVENT_TIME") ?: "Waktu Tidak Diketahui"
        val imageUrl = inputData.getString("EVENT_IMAGE") ?: ""

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Thread {
            try {
                val bitmap = Glide.with(applicationContext)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get()

                val notification = NotificationCompat.Builder(applicationContext, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(eventName)  // Nama event dari API
                    .setContentText("Jadwal: $eventTime")  // Waktu event dari API
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)  // Gambar dari API
                    )
                    .build()

                notificationManager.notify(1, notification)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
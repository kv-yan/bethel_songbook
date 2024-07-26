package ru.betel.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.betel.app.MainActivity
import ru.betel.app.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "weekly_notification_channel"
            val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.demo_sound)
            val channel = NotificationChannel(
                channelId,
                "Weekly Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for weekly notifications"
                enableLights(true)
                enableVibration(true)
                setSound(
                    soundUri,
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                )
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Create an Intent to open MainActivity with extras
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("is_new_template", true)
        }

        // Create a PendingIntent for the notification
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, "weekly_notification_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Հիշեցում Bethel երգացուցակ ծրագրից")
            .setContentText("Ստեղշեք նոր ցուցակ գալիք ծառայության համար")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set the PendingIntent
            .setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.demo_sound))

        notificationManager.notify(1, notificationBuilder.build())
    }
}

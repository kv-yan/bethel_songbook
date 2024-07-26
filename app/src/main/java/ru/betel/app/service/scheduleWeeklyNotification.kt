package ru.betel.app.service


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

fun scheduleWeeklyNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        set(Calendar.HOUR_OF_DAY, 17)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)

        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.DAY_OF_WEEK, 7)
        }
    }

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, pendingIntent
    )
}

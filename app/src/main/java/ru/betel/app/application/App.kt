package ru.betel.app.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.betel.app.R
import ru.betel.app.di.appModule
import ru.betel.app.di.daoModules
import ru.betel.app.di.dataModule
import ru.betel.app.di.databaseModule
import ru.betel.app.di.domainModule
import ru.betel.app.worker.SyncWorker
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startKoin {
            androidContext(this@App)
            modules(appModule, dataModule, domainModule, databaseModule, daoModules)
        }
        setupWorkManager()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "bethel_firebase_notification_channel"
            val channelName: CharSequence = "Channel Name"
            val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.demo_sound)

            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel Description"
                setSound(
                    soundUri,
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
                )
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.deleteNotificationChannel(channelId)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupWorkManager() {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(7, TimeUnit.DAYS).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncSongsWork", ExistingPeriodicWorkPolicy.KEEP, syncRequest
        )
    }
}
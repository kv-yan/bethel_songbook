package ru.betel.data.reopsitory.notification

import android.content.Context
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import ru.betel.data.extensions.getNotificationBody
import ru.betel.domain.constants.serverKey
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.notification.NotificationRepository
import java.io.IOException


class NotificationRepositoryImpl(private val context: Context) : NotificationRepository {
    private val TAG = "ERROR"
    override suspend fun sendNotification(template: SongTemplate) {
        val title = "${template.performerName}ը ավելացրել է նոր ցուցակ․"
        val body = template.getNotificationBody()
        val topic = "new_template"

        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("to", "/topics/$topic")
            put("notification", JSONObject().apply {
                put("title", title)
                put("body", body)
            })
/*
            put("data", JSONObject().apply {
                put("id", template.id)
                put("createDate", template.createDate)
                put("performerName", template.performerName)
                put("weekday", template.weekday)
                put("isSingleMode", template.isSingleMode)
                put("glorifyingSong", JSONArray(template.glorifyingSong.map { it.toJson() }))
                put("worshipSong", JSONArray(template.worshipSong.map { it.toJson() }))
                put("giftSong", JSONArray(template.giftSong.map { it.toJson() }))
                put("singleModeSongs", JSONArray(template.singleModeSongs.map { it.toJson() }))
            })
*/
        }

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), json.toString())

        val request = Request.Builder().url("https://fcm.googleapis.com/fcm/send").post(requestBody)
            .addHeader("Authorization", "key=$serverKey")
            .addHeader("Content-Type", "application/json").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "onFailure: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Notification sent successfully.")
                } else {
                    println("Failed to send notification.")
                }
            }
        })
    }

    fun Song.toJson(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("title", title)
            put("tonality", tonality)
            put("words", words)
            put("temp", temp)
            put("isGlorifyingSong", isGlorifyingSong)
            put("isWorshipSong", isWorshipSong)
            put("isGiftSong", isGiftSong)
            put("isFromSongbookSong", isFromSongbookSong)
        }
    }
}

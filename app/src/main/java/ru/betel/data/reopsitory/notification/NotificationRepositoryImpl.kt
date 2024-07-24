package ru.betel.data.reopsitory.notification

import android.content.Context
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import ru.betel.data.extensions.getNotificationBody
import ru.betel.domain.constants.serverKey
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.notification.NotificationRepository
import java.io.IOException


class NotificationRepositoryImpl(private val context: Context) : NotificationRepository {
    private val TAG = "ERROR"

    override suspend fun sendNotification(template: SongTemplate) {
        val title = "${template.performerName}ն ավելացրել է նոր ցուցակ․"
        val body = template.getNotificationBody()
//        val topic = "new_template"
        val topic = "test"
        val sound = "demo_sound"

        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("to", "/topics/$topic")
            put("notification", JSONObject().apply {
                put("title", title)
                put("body", body)
            })
            put("data", JSONObject().apply {
                put("template_id", template.id)
            })
        }

        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
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
                    Log.d(TAG, "Notification sent successfully.")
                } else {
                    val errorBody = response.body?.string()
                    Log.e(TAG, "Failed to send notification. Response: $errorBody")
                }
            }
        })
    }
}

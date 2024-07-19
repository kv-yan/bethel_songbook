package ru.betel.app.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.betel.data.extensions.parseTemplateData


class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val content = ""
//            val templateJson = intent.getStringExtra("template_data")
//            if (templateJson != null) {
//                val templateData: Map<String, String> = Gson().fromJson(
//                    templateJson,
//                    object : TypeToken<Map<String?, String?>?>() {}.type
//                )
//                val template = parseTemplateData(templateData)
//                content.plus(template)
//            }
            Text(text = content)
        }
    }

}
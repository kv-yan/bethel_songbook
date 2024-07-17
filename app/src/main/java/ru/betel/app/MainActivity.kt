package ru.betel.app

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get
import ru.betel.app.request.RequestNotificationPermission
import ru.betel.app.ui.bottom_sheet.LogInBottomSheet
import ru.betel.app.ui.screens.splash.SplashScreen
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        subscribeNotificationTopic()
        setContent {
            RequestNotificationPermission()

            val songViewModel: SongViewModel = get()
            val templateViewModel: TemplateViewModel = get()
            val settingsViewModel: SettingViewModel = get()
            val editViewModel: EditViewModel = get()
            val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val systemUiController = rememberSystemUiController()

            var isEndedSplashScreen by remember { mutableStateOf(false) }

            LaunchedEffect(isEndedSplashScreen) {
                delay(2500)
                isEndedSplashScreen = true
            }

            if (isEndedSplashScreen) {
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = actionBarColor, darkIcons = false
                    )
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                }
                LogInBottomSheet(
                    bottomSheetState = bottomSheetState,
                    songViewModel = songViewModel,
                    templateViewModel = templateViewModel,
                    settingsViewModel = settingsViewModel,
                    editViewModel = editViewModel
                ) {
                    restartApplication(this)
                }
            } else {
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent, darkIcons = false
                    )
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                }
                SplashScreen()
            }
        }
    }

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("songbook_v1", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }

    private fun restartApplication(activity: Activity) {
        val pm = activity.packageManager;
        val intent = pm.getLaunchIntentForPackage(activity.packageName);
        activity.finishAffinity()
        activity.startActivity(intent);
        exitProcess(0)
    }

    private fun subscribeNotificationTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic("new_template")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }
    }
}

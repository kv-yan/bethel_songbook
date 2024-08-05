package ru.betel.app

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
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
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get
import ru.betel.app.request.RequestNotificationPermission
import ru.betel.app.service.scheduleWeeklyNotification
import ru.betel.app.ui.bottom_sheet.LogInBottomSheet
import ru.betel.app.ui.screens.splash.SplashScreen
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    companion object {
        private const val REQUEST_CODE_OVERLAY_PERMISSION = 1001
        private val TAG = "NOTIFICATION"
    }

    private var overlayView: View? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleWeeklyNotification(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        subscribeNotificationTopic()
        setContent {
//            requestPermission()
            RequestNotificationPermission()

            val songViewModel: SongViewModel = get()
            val templateViewModel: TemplateViewModel = get()
            val settingsViewModel: SettingViewModel = get()
            val editViewModel: EditViewModel = get()
            val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val systemUiController = rememberSystemUiController()
            val navController = rememberNavController()
            val actionBarState = remember { mutableStateOf(ActionBarState.HOME_SCREEN) }

            templateViewModel.loadTemplate()
            songViewModel.loadSong()

            var isEndedSplashScreen by remember { mutableStateOf(false) }
            val templateUiState by templateViewModel.templateUiState

            LaunchedEffect(isEndedSplashScreen) {
                delay(2500)
                isEndedSplashScreen = true
            }

            LaunchedEffect(templateUiState, isEndedSplashScreen) {
                if (isEndedSplashScreen && templateUiState.isNotEmpty()) {
                    intent.extras?.get("template_id")?.let { id ->
                        Log.e(TAG, "onCreate: ********* id :: $id")
                        templateViewModel.templateUiState.value.forEach { template ->
                            if (template.id == id) {
                                templateViewModel.singleTemplate.value = template
                                navController.navigate(Screens.SINGLE_TEMPLATE_SCREEN.route)
                                actionBarState.value = ActionBarState.SINGLE_TEMPLATE_SCREEN
                            }
                        }
                    }

                    intent.extras?.get("is_new_template").let {
                        if (it == true) {
                            navController.navigate(Screens.NEW_TEMPLATE_SCREEN.route)
                            actionBarState.value = ActionBarState.NEW_TEMPLATE_SCREEN
                        }
                    }
                }
            }
            if (isEndedSplashScreen) {
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = settingsViewModel.appTheme.value.actionStatusBarColor,
                        darkIcons = false
                    )
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                }
                LogInBottomSheet(
                    bottomSheetState = bottomSheetState,
                    songViewModel = songViewModel,
                    templateViewModel = templateViewModel,
                    settingsViewModel = settingsViewModel,
                    editViewModel = editViewModel,
                    navController = navController,
                    actionBarState = actionBarState,
                    appTheme = settingsViewModel.appTheme.value
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
//                SplashScreen(getVideoUri())
                SplashScreen(settingsViewModel.appTheme.value)
            }
        }
    }

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("songbook_v1", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }

    private fun restartApplication(activity: Activity) {
        val pm = activity.packageManager
        val intent = pm.getLaunchIntentForPackage(activity.packageName)
        activity.finishAffinity()
        activity.startActivity(intent)
        exitProcess(0)
    }

    private fun subscribeNotificationTopic() {
//        FirebaseMessaging.getInstance().subscribeToTopic("new_template")
        FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener { task ->
            if (!task.isSuccessful) {
            }
        }
    }

    /*
        private fun requestPermission() {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
            } else {
    //            showOverlay()
            }

        }
    */

    /*
        @RequiresApi(Build.VERSION_CODES.O)
        @Deprecated("Deprecated in Java")
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
                if (Settings.canDrawOverlays(this)) {
                    showOverlay()
                } else {
                }
            }
        }
    */


    /*
        @RequiresApi(Build.VERSION_CODES.O)
        private fun showOverlay() {
            if (overlayView != null) return
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

            val layoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

            layoutParams.gravity = Gravity.CENTER

            val view = LayoutInflater.from(this).inflate(R.layout.reminder_layout, null)
            overlayView = view

            view.findViewById<Button>(R.id.button_open).setOnClickListener {
                windowManager.removeView(view)
            }

            view.findViewById<TextView>(R.id.button_close).setOnClickListener {
                windowManager.removeView(view)
            }

            windowManager.addView(view, layoutParams)
        }
    */


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (overlayView != null) {
            windowManager.removeView(overlayView)
            overlayView = null
        } else {
            super.onBackPressed()
        }
    }
}



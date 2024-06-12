package ru.betel.app

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import org.koin.androidx.compose.get
import ru.betel.app.ui.bottom_sheet.LogInBottomSheet
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
        setContent {
            val songViewModel: SongViewModel = get()
            val templateViewModel: TemplateViewModel = get()
            val settingsViewModel: SettingViewModel = get()
            val editViewModel: EditViewModel = get()

            val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            LogInBottomSheet(
                bottomSheetState = bottomSheetState,
                songViewModel = songViewModel,
                templateViewModel = templateViewModel,
                settingsViewModel = settingsViewModel,
                editViewModel = editViewModel
            ) {
                restartApplication(this)
            }
        }
    }

    private fun restartApplication(activity: Activity) {
        val pm = activity.packageManager;
        val intent = pm.getLaunchIntentForPackage(activity.packageName);
        activity.finishAffinity()
        activity.startActivity(intent);
        exitProcess(0)
    }
}

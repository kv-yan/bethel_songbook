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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.koin.androidx.compose.get
import ru.betel.app.ui.bottom_sheet.LogInBottomSheet
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.SearchAppBarState
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

            val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            LogInBottomSheet(
                bottomSheetState = bottomSheetState,
                songViewModel = songViewModel,
                templateViewModel = templateViewModel,
                settingsViewModel = settingsViewModel
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

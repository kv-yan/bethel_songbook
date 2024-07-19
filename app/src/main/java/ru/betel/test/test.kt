import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.get
import ru.betel.app.ui.action_bar.HomeActionBar
import ru.betel.app.ui.screens.home.HomeScreen
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.SearchAppBarState

@Preview
@Composable
private fun TestModes() {
    val navController = rememberNavController()
    val songViewModel = get<SongViewModel>()
    val editViewModel = get<EditViewModel>()
    val settingViewModel = get<SettingViewModel>()
    val searchAppBarState = remember { mutableStateOf(SearchAppBarState.CLOSED) }
    val actionBarState = remember { mutableStateOf(ActionBarState.HOME_SCREEN) }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            HomeActionBar(
                navController = navController,
                searchAppBarState = searchAppBarState,
                songViewModel = songViewModel,
                settingViewModel = settingViewModel ,
                appTheme = settingViewModel.appTheme.value,
                textSize = settingViewModel.songbookTextSize,
                onMenuIconClick = { /*TODO*/ }, ) {
            }

            HomeScreen(
                navController = navController,
                actionBarState = actionBarState ,
                viewModel = songViewModel,
                settingViewModel =settingViewModel ,
                editViewModel = editViewModel
            )
        }
    }
}
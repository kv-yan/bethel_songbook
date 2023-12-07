package ru.betel.app.ui.screens.template

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.betel.app.ui.items.template.SongTemplateColumItem
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import ru.betel.domain.model.ui.TemplateType

@Composable
fun TemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
) {
    actionBarState.value = ActionBarState.TEMPLATE_SCREEN
    val templateSelectedCategory = viewModel.templateSelectedType

    val templates = viewModel.templateState.collectAsState()

    val localTemplate = viewModel.localTemplateState.observeAsState(mutableListOf())

    val isLoading = viewModel.isLoadingContainer.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading.value)

    if (templates.value.isEmpty()) {
        LaunchedEffect(key1 = null) { viewModel.refresh(2000L) }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = viewModel::loadTemplate,
        refreshTriggerDistance = 40.dp,
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (templateSelectedCategory.value == TemplateType.ALL) {
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(templates.value) {
                        SongTemplateColumItem(it, settingViewModel.songbookTextSize) {
                            viewModel.singleTemplate.value = it
                            navController.navigate(Screens.SINGLE_TEMPLATE_SCREEN.route)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(localTemplate.value) {
                        SongTemplateColumItem(it, settingViewModel.songbookTextSize) {
                            viewModel.singleTemplate.value = it
                            navController.navigate(Screens.SINGLE_TEMPLATE_SCREEN.route)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        BackHandler {
            navController.popBackStack()
        }
    }
}

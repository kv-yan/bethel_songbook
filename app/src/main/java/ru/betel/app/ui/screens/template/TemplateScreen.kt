package ru.betel.app.ui.screens.template

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.betel.app.R
import ru.betel.app.ui.items.template.TemplateColumnItem
import ru.betel.app.ui.items.template.TemplateDetailsState
import ru.betel.app.ui.widgets.NothingFoundScreen
import ru.betel.app.ui.widgets.loading_anim.LoadingScreen
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import ru.betel.domain.model.ui.TemplateType

@SuppressLint("UnrememberedMutableState")
@Composable
fun TemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
) {
    actionBarState.value = ActionBarState.TEMPLATE_SCREEN
    val appTheme = settingViewModel.appTheme.value
    val templates by viewModel.templateUiState
    val localTemplate by viewModel.localTemplateState.observeAsState(mutableListOf())
    val searchQuery by viewModel.searchQuery
    val performerNameFilter by viewModel.performerNameFilter

    val isLoading = false
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val detailsState = remember { mutableStateOf(TemplateDetailsState.Closed) }
    detailsState.value = if (viewModel.isOpeningAllTemplate.value) {
        TemplateDetailsState.Opened
    } else {
        TemplateDetailsState.Closed
    }

    if (templates.isEmpty()) {
        LaunchedEffect(key1 = null) { viewModel.loadTemplate() }
    }

    val filteredTemplates = viewModel.searchTemplates(searchQuery)
        .filter { it.performerName.contains(performerNameFilter, ignoreCase = true) }

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .background(appTheme.screenBackgroundColor),
        state = swipeRefreshState,
        onRefresh = viewModel::loadTemplate,
        refreshTriggerDistance = 40.dp,
    ) {
        Box(contentAlignment = Alignment.Center) {
            val itemsList =
                if (viewModel.templateSelectedType.value == TemplateType.ALL) filteredTemplates else localTemplate

            when {
                viewModel.templateSelectedType.value == TemplateType.MINE && itemsList.isEmpty() -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Չկան պահպանված ցուցակներ", style = TextStyle(
                                    fontSize = settingViewModel.songbookTextSize.normalItemDefaultTextSize,
                                    lineHeight = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                    fontWeight = FontWeight(500),
                                    color = appTheme.primaryTextColor,
                                )
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_template_active),
                                contentDescription = "image description",
                                tint = appTheme.primaryTextColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Surface(modifier = Modifier.fillMaxWidth(),
                            color = appTheme.screenBackgroundColor,
                            shape = RoundedCornerShape(12.dp),
                            onClick = { navController.navigate(Screens.NEW_TEMPLATE_SCREEN.route) }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Ավելացնել նոր ցուցակ", style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                        fontWeight = FontWeight(400),
                                        color = appTheme.primaryTextColor,
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    painter = painterResource(R.drawable.ic_add_new_template),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp),
                                    tint = appTheme.primaryTextColor
                                )
                            }

                        }
                    }
                }

                itemsList.isEmpty() && searchQuery.isEmpty() && performerNameFilter.isEmpty() -> {
                    LoadingScreen(appTheme)
                }

                itemsList.isEmpty() && (searchQuery.isNotEmpty() || performerNameFilter.isNotEmpty()) -> {
                    NothingFoundScreen(appTheme)
                }

                else -> {
                    val reversedItemsList = itemsList.reversed()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(appTheme.screenBackgroundColor)
                    ) {
                        item {
                            val template = reversedItemsList.firstOrNull()
                            template?.let {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    color = appTheme.screenBackgroundColor,
                                    shape = RoundedCornerShape(12.dp),
                                    onClick = {
                                        viewModel.singleTemplate.value = it
                                        navController.navigate(Screens.SINGLE_TEMPLATE_SCREEN.route)
                                    }
                                ) {
                                    Column(modifier = Modifier.padding(bottom = 6.dp)) {
                                        Text(
                                            text = "Նոր",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 12.dp, top = 10.dp),
                                            style = TextStyle(
                                                fontSize = settingViewModel.songbookTextSize.smallItemDefaultTextSize,
                                                color = appTheme.secondaryTextColor
                                            )
                                        )
                                        TemplateColumnItem(
                                            detailsState = mutableStateOf(TemplateDetailsState.Opened),
                                            appTheme = appTheme,
                                            template = it,
                                            textSize = settingViewModel.songbookTextSize,
                                            searchQuery = viewModel.searchQuery.value,
                                        ) {
                                            viewModel.singleTemplate.value = it
                                            navController.navigate(Screens.SINGLE_TEMPLATE_SCREEN.route)
                                        }
                                    }
                                }
                            }
                        }
                        items(reversedItemsList.drop(1), key = { it.id }) { template ->
                            TemplateColumnItem(
                                detailsState = detailsState,
                                appTheme = appTheme,
                                template = template,
                                textSize = settingViewModel.songbookTextSize,
                                searchQuery = viewModel.searchQuery.value,
                            ) {
                                viewModel.singleTemplate.value = template
                                navController.navigate(Screens.SINGLE_TEMPLATE_SCREEN.route)
                            }
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }
            }
        }
    }
    BackHandler {
        navController.popBackStack()
    }
}

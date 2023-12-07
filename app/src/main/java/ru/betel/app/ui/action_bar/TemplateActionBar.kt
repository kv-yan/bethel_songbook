package ru.betel.app.ui.action_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.get
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.widgets.SearchTopAppBar
import ru.betel.app.ui.widgets.dropdown_menu.AddNewItemDropdownMenu
import ru.betel.app.ui.widgets.dropdown_menu.TemplateDropdownMenu
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.SearchAppBarState
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
private fun ActionBarContent(
    navController: NavController,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
    onSearchClicked: () -> Unit,
    onMenuIconClick: () -> Unit,
    onSettingsBtnClick: () -> Unit,
) {
    Surface(
        color = actionBarColor, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onMenuIconClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "menu_btn",
                    tint = Color.White,
                    modifier = Modifier
                        .width(18.dp)
                        .height(10.dp)
                )
            }

            Text(
                text = "Ցուցակներ", style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                )
            )

            // action btn part
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onSearchClicked() }, modifier = Modifier.size(27.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search btn",
                        tint = Color.White,
                        modifier = Modifier
                            .width(16.dp)
                            .height(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                val isShowTemplateMenu = remember {
                    mutableStateOf(false)
                }

                IconButton(
                    onClick = { isShowTemplateMenu.value = true }, modifier = Modifier.size(25.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_category_more),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(16.dp)
                            .height(12.dp)
                    )
                }
                TemplateDropdownMenu(
                    expanded = isShowTemplateMenu,
                    templateViewModel.templateSelectedType, templateViewModel = templateViewModel
                )

                Spacer(modifier = Modifier.width(20.dp))


                val expanded = remember { mutableStateOf(false) }
                /******* Dropdown Menu *******/
                AddNewItemDropdownMenu(
                    expanded = expanded,
                    navController = navController,
                    userStatus = settingViewModel.checkUserLoginStatus.value
                )

                IconButton(
                    onClick = { expanded.value = true },
                    modifier = Modifier
                        .width(12.dp)
                        .height(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_item),
                        contentDescription = "image description",
                        tint = Color.White,
                        modifier = Modifier
                            .width(12.dp)
                            .height(12.dp)
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                IconButton(onClick = { onSettingsBtnClick() }, modifier = Modifier.size(27.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "add new item btn",
                        tint = Color.White,
                        modifier = Modifier
                            .width(3.dp)
                            .height(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }

}


@Composable
fun TemplateActionBar(
    searchAppBarState: MutableState<SearchAppBarState>,
    navController: NavController,
    settingViewModel: SettingViewModel,
    templateViewModel: TemplateViewModel,
    textSize: SongbookTextSize,
    onMenuIconClick: () -> Unit,
    onSettingsBtnClick: () -> Unit,
) {
    val viewModel: SongViewModel = get()

    when (searchAppBarState.value) {
        SearchAppBarState.CLOSED -> {
            ActionBarContent(navController = navController,
                templateViewModel = templateViewModel,
                settingViewModel = settingViewModel,
                onSearchClicked = {
                    searchAppBarState.value = SearchAppBarState.OPENED
                },
                onMenuIconClick = { onMenuIconClick() },
                onSettingsBtnClick = { onSettingsBtnClick() })
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = actionBarColor)
                    .padding(horizontal = 11.dp, vertical = 6.dp)
            ) {
                SearchTopAppBar(text = viewModel.searchAppBarText, onTextChange = { text ->
                    viewModel.searchAppBarText.value = text
                }, onCloseClicked = {
                    searchAppBarState.value = SearchAppBarState.CLOSED
                    viewModel.searchAppBarText.value = ""
                }, textSize = textSize)
            }
        }
    }
}

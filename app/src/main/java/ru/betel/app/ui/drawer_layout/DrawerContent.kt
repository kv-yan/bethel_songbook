package ru.betel.app.ui.drawer_layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DrawerState
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.betel.app.R
import ru.betel.app.ui.items.menu.DrawerMenuItem
import ru.betel.app.ui.theme.dividerColor
import ru.betel.app.ui.theme.drawerLayoutSecondaryColor
import ru.betel.domain.model.ui.MenuItem
import ru.betel.domain.model.ui.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,
    onLogInBtnClick: () -> Unit,
) {
    val models = remember {
        mutableStateOf<List<MenuItem>>(
            mutableListOf(
                MenuItem(
                    "Երգեր",
                    R.drawable.ic_home_active,
                    R.drawable.ic_home_passive,
                    mutableStateOf(true),
                    Screens.HOME_SCREEN
                ), MenuItem(
                    "Ցուցակներ",
                    R.drawable.ic_template_active,
                    R.drawable.ic_template_passive,
                    mutableStateOf(false),
                    Screens.TEMPLATE_SCREEN
                ), MenuItem(
                    "Կատեգորիաներ",
                    R.drawable.ic_category_active,
                    R.drawable.ic_category_pasive,
                    mutableStateOf(false),
                    Screens.CATEGORY_SCREEN
                ), MenuItem(
                    "Ընտրվածներ",
                    R.drawable.ic_menu_bookmark_selected,
                    R.drawable.ic_menu_bookmark,
                    mutableStateOf(false),
                    Screens.FAVORITE_SCREEN
                )
            )
        )
    }
    val loginItem = MenuItem(
        title = "Մուտք",
        activeIcon = R.drawable.ic_log_in,
        passiveIcon = R.drawable.ic_log_in,
        isSelected = remember {
            mutableStateOf(false)
        },
        screen = Screens.CATEGORY_SCREEN // TODO: bottom sheet
    )

    Scaffold(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(), contentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // Image part
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(drawerLayoutSecondaryColor),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.padding(20.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo_transparent),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Column(modifier = Modifier.padding(start = 11.dp, top = 20.dp)) {
                        Text(
                            text = "Բեթհել Մրգաշատ",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            fontFamily = FontFamily(Font(R.font.mardoto_medium))
                        )
                        Text(
                            text = "2023",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.mardoto_medium))
                        )
                    }
                }
            }
//            Spacer(modifier = Modifier.height(24.dp))


            // Menu part
            LazyColumn(
                modifier = Modifier
                    .padding(top = 11.dp)
                    .heightIn(min = 50.dp, max = 200.dp),
            ) {
                itemsIndexed(models.value) { index, item ->
                    DrawerMenuItem(item = item) {
                        scope.launch { drawerState.close() }
                        models.value.forEach { it.isSelected.value = false }
                        item.isSelected.value = true
                        navController.navigate(item.screen.route)
                    }
                }

            }
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column() {
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = dividerColor)
                    )
                    DrawerMenuItem(
                        item = loginItem
                    ) {
                        scope.launch {
                            drawerState.close()
                            delay(50)
                            onLogInBtnClick()
                        }

                    }
                }
            }
        }

        it
    }

}
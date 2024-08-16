package ru.betel.app.ui.drawer_layout

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.betel.app.R
import ru.betel.app.ui.items.menu.DrawerMenuItem
import ru.betel.app.ui.theme.dividerColor
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.MenuItem
import ru.betel.domain.model.ui.Screens


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    appTheme: AppTheme,
    screenState: MutableState<Screens>,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,
    onLogInBtnClick: () -> Unit,
) {
    val menuItems = listOf(
        MenuItem(
            title = "Երգեր",
            activeIcon = R.drawable.ic_home_active,
            passiveIcon = R.drawable.ic_home_passive,
            isSelected = mutableStateOf(screenState.value == Screens.HOME_SCREEN),
            screen = Screens.HOME_SCREEN
        ), MenuItem(
            "Ցուցակներ",
            R.drawable.ic_template_active,
            R.drawable.ic_template_passive,
            mutableStateOf(screenState.value == Screens.TEMPLATE_SCREEN),
            Screens.TEMPLATE_SCREEN
        ), MenuItem(
            "Կատեգորիաներ",
            R.drawable.ic_category_active,
            R.drawable.ic_category_pasive,
            mutableStateOf(screenState.value == Screens.CATEGORY_SCREEN),
            Screens.CATEGORY_SCREEN
        ), MenuItem(
            "Ընտրվածներ",
            R.drawable.ic_menu_bookmark_selected,
            R.drawable.ic_menu_bookmark,
            mutableStateOf(screenState.value == Screens.FAVORITE_SCREEN),
            Screens.FAVORITE_SCREEN
        )
    )

    val loginItem = MenuItem(
        title = "Մուտք",
        activeIcon = R.drawable.ic_log_in,
        passiveIcon = R.drawable.ic_log_in,
        isSelected = mutableStateOf(false),
        screen = Screens.CATEGORY_SCREEN
    )

    Scaffold(
        Modifier.fillMaxSize(), contentColor = appTheme.screenBackgroundColor
    ) {
        Column(
            modifier = Modifier
                .background(appTheme.screenBackgroundColor)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Image part
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appTheme.drawerFieldSelectedColor),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.padding(20.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.des_1),
                        contentDescription = null,
                        tint = appTheme.drawerSelectedIconColor,
                        modifier = Modifier.size(80.dp)
                    )
                    Column(modifier = Modifier.padding(start = 11.dp, top = 20.dp)) {
                        Text(
                            text = "Բեթել Մրգաշատ",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                            color = appTheme.secondaryTextColor
                        )
                        Text(
                            text = "2023",
                            fontSize = 12.sp,
                            color = appTheme.secondaryTextColor,
                            fontFamily = FontFamily(Font(R.font.mardoto_medium))
                        )
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Դուք ադմին էք",
                                fontSize = 11.sp,
                                color = appTheme.secondaryTextColor,
                                modifier = Modifier.padding(bottom = 4.dp),
                                fontFamily = FontFamily(Font(R.font.mardoto_medium))
                            )
                        }
                    }
                }
            }

            // Menu part
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appTheme.screenBackgroundColor)
                    .padding(top = 11.dp)
                    .heightIn(min = 50.dp, max = 200.dp),
            ) {
                itemsIndexed(menuItems) { index, item ->
                    DrawerMenuItem(item = item, appTheme) {
                        scope.launch { drawerState.close() }
                        menuItems.forEach { it.isSelected.value = false }
                        item.isSelected.value = true
                        screenState.value = item.screen
                        navController.navigate(item.screen.route)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(appTheme.screenBackgroundColor)
                    .height(400.dp)
                    .fillMaxHeight()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = dividerColor)
                    )
                    DrawerMenuItem(
                        item = loginItem, appTheme
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
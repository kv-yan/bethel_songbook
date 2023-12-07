package ru.betel.app.ui.action_bar

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
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.R
import ru.betel.app.ui.widgets.dropdown_menu.AddNewItemDropdownMenu
import ru.betel.app.view_model.settings.SettingViewModel

@Composable
fun SingleTemplateActionBar(
    navController: NavController,
    settingViewModel: SettingViewModel,
    onSettingsBtnClick: () -> Unit,
    onShareBtnClick: () -> Unit,
) {
    Surface(
        color = actionBarColor, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_go_back),
                    contentDescription = "go back",
                    tint = Color.White,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
            }


            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(
                    onClick = { onShareBtnClick() }, modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "image description",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
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

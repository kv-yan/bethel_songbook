package ru.betel.app.ui.action_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.widgets.dropdown_menu.AddNewItemDropdownMenu
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.Screens

@Composable
fun SingleTemplateActionBar(
    navController: NavController,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel,
    templateViewModel: TemplateViewModel,
    onSettingsBtnClick: () -> Unit,
    onShareBtnClick: () -> Unit,
    onDeleteBtnClick: (SongTemplate) -> Unit,
    onNotificationBtnClick: (SongTemplate) -> Unit,
    onUploadBtnClick: (SongTemplate) -> Unit,
) {
    val appTheme = settingViewModel.appTheme.value

    Surface(
        color = appTheme.actionBarColor, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                editViewModel.cleanFields()
                templateViewModel.cleanFields()
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_go_back),
                    contentDescription = "go back",
                    tint = appTheme.actionBarIconColor,
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
                        .width(24.dp)
                        .height(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "image description",
                        tint = appTheme.actionBarIconColor,
                        modifier = Modifier.size(17.dp)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(
                    onClick = { onShareBtnClick() }, modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "image description",
                        tint = appTheme.actionBarIconColor,
                        modifier = Modifier.size(16.dp)
                    )
                }


                if (FirebaseAuth.getInstance().currentUser != null) {
                    var templateId = null as Int?
                    try {
                        templateId = templateViewModel.singleTemplate.value.id.toInt()

                    }catch (ex : NumberFormatException){
                        ex.printStackTrace()
                    }
                    if (templateId != null) {
                        if (templateId >=  0) {
                            Spacer(modifier = Modifier.width(20.dp))
                            IconButton(
                                onClick = { onUploadBtnClick(templateViewModel.singleTemplate.value) }, modifier = Modifier.size(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_upload),
                                    contentDescription = "image description",
                                    tint = appTheme.actionBarIconColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }



                    Spacer(modifier = Modifier.width(20.dp))
                    IconButton(
                        onClick = {
                            onNotificationBtnClick(templateViewModel.singleTemplate.value)
                        }, modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Send notification",
                            tint = appTheme.actionBarIconColor,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(19.dp))
                    IconButton(
                        onClick = {
                            editViewModel.currentTemplate.value =
                                templateViewModel.singleTemplate.value
                            navController.navigate(Screens.EDIT_TEMPLATE_SCREEN.route)
                        }, modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit song",
                            tint = appTheme.actionBarIconColor,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(19.dp))
                    IconButton(
                        onClick = {
                            onDeleteBtnClick(templateViewModel.singleTemplate.value)
                        }, modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Edit song",
                            tint = appTheme.actionBarIconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.width(18.dp))
                IconButton(onClick = { onSettingsBtnClick() }, modifier = Modifier.size(27.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "add new item btn",
                        tint = appTheme.actionBarIconColor,
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

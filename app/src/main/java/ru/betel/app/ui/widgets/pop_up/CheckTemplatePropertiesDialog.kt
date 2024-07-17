package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode.LOCAL
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode.SERVER
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.SongTemplate

enum class TemplateSaveMode() {
    LOCAL, SERVER
}


@Composable
fun CheckTemplatePropertiesDialog(
    isShowDialog: MutableState<Boolean>,
    viewModel: TemplateViewModel,
    onSaveClick: (TemplateSaveMode,SongTemplate, Boolean) -> Unit
) {
    val isSelectedSelfVisibility = remember { mutableStateOf(false) }
    val isSendNotificationToAllUsers = remember { mutableStateOf(false) }
    val createdNewTemplate = viewModel.createdNewTemplate
    val onDismiss = { isShowDialog.value = !isShowDialog.value }
    if (isShowDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, buttons = {
            Surface(shape = RoundedCornerShape(15.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        text = "Որոշեք թե որտեղ պահել երգը", style = TextStyle(
                            fontSize = 14.5.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                            fontWeight = FontWeight(500),
                            color = Color.Black,
                        ), modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth()
                    )
                    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                        }) {
                        RadioButton(
                            selected = isSelectedSelfVisibility.value, onClick = {
                                isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                            }, colors = RadioButtonDefaults.colors(selectedColor = actionBarColor)
                        )
                        Text(
                            text = "Տեսանելի միայն ինձ", style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                            ), modifier = Modifier
                                .padding(start = 6.dp)
                                .fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                        }) {
                        RadioButton(
                            selected = !isSelectedSelfVisibility.value, onClick = {
                                isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                            }, colors = RadioButtonDefaults.colors(selectedColor = actionBarColor)
                        )
                        Text(
                            text = "Տեսանելի բոլորին", style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                            ), modifier = Modifier
                                .padding(start = 6.dp)
                                .fillMaxWidth()
                        )
                    }

                    if (!isSelectedSelfVisibility.value) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 3.dp)
                                .clickable {
                                    isSendNotificationToAllUsers.value =
                                        !isSendNotificationToAllUsers.value
                                }) {
                            Checkbox(
                                checked = isSendNotificationToAllUsers.value, onCheckedChange = {
                                    isSendNotificationToAllUsers.value =
                                        !isSendNotificationToAllUsers.value
                                }, colors = CheckboxDefaults.colors(checkedColor = actionBarColor)
                            )
                            Text(
                                text = "Ուղարկել բոլորին ծանուցում", style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color.Black,
                                ), modifier = Modifier.padding(start = 3.dp)
                            )
                        }

                    }

                    SaveButton {
                        val saveMode = if (isSelectedSelfVisibility.value) LOCAL else SERVER
                        if (createdNewTemplate.value != null){
                            onSaveClick(saveMode,
                                createdNewTemplate.value!!, isSendNotificationToAllUsers.value)
                        }
                    }

                }
            }
        })
    }
}


@Preview
@Composable
fun CheckTemplatePropertiesPrev() {
    val isShowing = remember {
        mutableStateOf(
            true
        )
    }


}


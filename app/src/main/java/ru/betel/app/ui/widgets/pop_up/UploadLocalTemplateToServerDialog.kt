package ru.betel.app.ui.widgets.pop_up

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.dropdown_menu.WeekdayDropDownMenu
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.AppTheme

@Composable
fun UploadLocalTemplateToServerDialog(
    showDialog: MutableState<Boolean>,
    template: MutableState<SongTemplate>,
    onConfirmationClick: (SongTemplate) -> Unit,
) {
    val onDismiss = { showDialog.value = false }
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, title = {
            val dialogTitle = buildAnnotatedString {
                append("Ցանկանում էք այս ցուցակը հասանելի դարձնել բոլորին")
            }
            Text(text = dialogTitle, fontSize = 14.sp)
        }, text = {
            Text(text = "Եթէ ցանկանում եք , սեղմեք ՛Ուղարկել՛", fontSize = 14.sp)
        }, confirmButton = {
            Text("Ուղարկել", color = actionBarColor, modifier = Modifier.clickable {
                onConfirmationClick(template.value)
                onDismiss()
            })
        }, dismissButton = {
            Text("Չեղարկել", color = actionBarColor, modifier = Modifier.clickable {
                onDismiss()
            })
        })
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UploadDialog(
    appTheme: AppTheme,
    templateViewModel: TemplateViewModel,
    showDialog: MutableState<Boolean>,
    template: MutableState<SongTemplate>,
    onConfirmationClick: (SongTemplate, Boolean) -> Unit,
) {

    val performerName = remember { mutableStateOf("") }
    val weekday = remember { mutableStateOf("Շաբաթվա օր") }
    val date = remember { mutableStateOf("Ամսաթիվ") }

    val isShowingDayDialog = remember { mutableStateOf(false) }
    val isShowingWeekdayDialog = remember { mutableStateOf(false) }
    val isSendNotification = remember { mutableStateOf(false) }

    LaunchedEffect(template.value) {
        performerName.value = templateViewModel.getPerformerName()
    }

    val onDismissRequest = {
        showDialog.value = false
        isShowingDayDialog.value = false
        isShowingWeekdayDialog.value = false
    }

    if (showDialog.value) {
        Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = appTheme.screenBackgroundColor
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        MyTextFields(
                            appTheme = appTheme,
                            placeholder = "Առաջնորդ",
                            fieldText = performerName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(25.dp)
                        ) {
                            templateViewModel.setPerformerName(it)
                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                    ) {
                        Surface(
                            modifier = Modifier.clickable {
                                isShowingWeekdayDialog.value = true
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = appTheme.fieldBackgroundColor
                        ) {
                            WeekdayDropDownMenu(
                                appTheme = appTheme,
                                selectedDay = weekday,
                                modifier = Modifier.fillMaxWidth(0.49f)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Surface(modifier = Modifier
                            .clickable {
                                isShowingDayDialog.value = true
                            }
                            .fillMaxWidth()
                            .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = appTheme.fieldBackgroundColor) {
                            DayPickerDialog(
                                appTheme = appTheme, isShowing = isShowingDayDialog, dayState = date
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 3.dp)
                            .clickable {
                                isSendNotification.value = !isSendNotification.value
                            }) {
                        Checkbox(
                            checked = isSendNotification.value, onCheckedChange = {
                                isSendNotification.value = !isSendNotification.value
                            }, colors = CheckboxDefaults.colors(
                                checkedColor = appTheme.primaryTextColor,
                                checkmarkColor = appTheme.fieldBackgroundColor,
                                uncheckedColor = appTheme.secondaryTextColor
                            )
                        )
                        Text(
                            text = "Ուղարկել բոլորին ծանուցում", style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(500),
                                color = if (isSendNotification.value) appTheme.primaryTextColor else appTheme.secondaryTextColor,
                            ), modifier = Modifier.padding(start = 3.dp)
                        )
                    }


                    SaveButton(appTheme = appTheme) {
                        template.value = template.value.copy(
                            id = "SongTemplate",
                            performerName = performerName.value,
                            createDate = date.value,
                            weekday = weekday.value
                        )
                        onConfirmationClick(template.value, isSendNotification.value)
                        onDismissRequest()
                    }

                }
            }
        }
    }
}
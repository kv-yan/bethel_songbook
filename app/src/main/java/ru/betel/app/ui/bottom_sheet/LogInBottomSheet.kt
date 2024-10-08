package ru.betel.app.ui.bottom_sheet

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.betel.app.R
import ru.betel.app.ui.drawer_layout.MenuDrawerLayout
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.loading_anim.LoadingPopUp
import ru.betel.app.ui.widgets.pop_up.NoInternetConnectionDialog
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SearchAppBarState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LogInBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingsViewModel: SettingViewModel,
    editViewModel: EditViewModel,
    actionBarState: MutableState<ActionBarState>,
    navController: NavHostController,
    appTheme: AppTheme,
    onAppRestart: () -> Unit,
) {
    val loginText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val networkState = remember { mutableStateOf(!settingsViewModel.networkState.value) }

    val isLogInBottomSheetState = remember {
        mutableStateOf(true)
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appTheme.backgroundColor)
        ) {
            ModalBottomSheetLayout(
                sheetBackgroundColor = appTheme.screenBackgroundColor,
                sheetState = bottomSheetState,
                sheetContent = {
                    if (isLogInBottomSheetState.value) {
                        LoginBottomSheetContent(bottomSheetState = bottomSheetState,
                            appTheme = settingsViewModel.appTheme.value,
                            scope = scope,
                            loginText = loginText,
                            passwordText = passwordText,
                            onLogInBtnClick = {
                                settingsViewModel.signInWithEmailAndPassword(
                                    loginText.value, passwordText.value
                                )
                            },
                            onActivityRestart = {
                                onAppRestart()
                            })
                    } else {
                        SettingsBottomSheetContent(
                            modes = settingsViewModel.modes, settingsViewModel
                        )
                    }
                },
                sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appTheme.backgroundColor),
                    color = appTheme.backgroundColor
                ) {

                    val searchAppBarState = remember { mutableStateOf(SearchAppBarState.CLOSED) }
                    MenuDrawerLayout(navController = navController,
                        searchAppBarState = searchAppBarState,
                        songViewModel = songViewModel,
                        templateViewModel = templateViewModel,
                        settingViewModel = settingsViewModel,
                        editViewModel = editViewModel,
                        appTheme = appTheme,
                        onSettingsBtnClick = {
                            scope.launch {
                                isLogInBottomSheetState.value = false
                                bottomSheetState.show()
                            }
                        },
                        textSize = settingsViewModel.songbookTextSize,
                        actionBarState = actionBarState,
                        onLogInBtnClick = {
                            scope.launch {
                                isLogInBottomSheetState.value = true
                                bottomSheetState.show()
                            }
                        })
                    NoInternetConnectionDialog(networkState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginBottomSheetContent(
    appTheme: AppTheme,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    loginText: MutableState<String>,
    passwordText: MutableState<String>,
    onLogInBtnClick: () -> Unit,
    onActivityRestart: () -> Unit,
) {
    val isShowingLoadingState = remember { mutableStateOf(false) }
    val isShowingToast = remember { mutableStateOf(false) }
    val toastMsg = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {
        Text(
            text = "Մուտք", style = TextStyle(
                fontSize = 26.sp,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                fontWeight = FontWeight(700),
                color = appTheme.primaryTextColor,
            ), modifier = Modifier.padding(top = 30.dp, start = 20.dp, bottom = 16.dp)
        )
        MyTextFields(
            appTheme = appTheme,
            placeholder = "Մուտքանուն",
            modifier = Modifier.fillMaxWidth(),
            imeAction = ImeAction.Next,
            fieldText = loginText,
            singleLine = false
        )

        Spacer(modifier = Modifier.height(10.dp))

        MyTextFields(
            appTheme = appTheme,
            placeholder = "Գաղտնաբառ",
            modifier = Modifier.fillMaxWidth(),
            textType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            fieldText = passwordText,
            singleLine = false
        )
        Spacer(modifier = Modifier.height(20.dp))

        SaveButton(
            appTheme = appTheme,
            text = "Մուտք",
            onClick = {
                if (loginText.value.isNotEmpty() && passwordText.value.isNotEmpty()) {
                    scope.launch {
                        onLogInBtnClick()
                        isShowingLoadingState.value = true
                        delay(3000)
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            bottomSheetState.hide()
                            toastMsg.value = "Բարի գալուս ադմին ռեժիմ"
                            isShowingToast.value = true
                            delay(1000)
                            onActivityRestart()
                        } else {
                            toastMsg.value = "Չհաջողվեց մուտք գործել որպես ադմին"
                            isShowingToast.value = true

                        }
                        isShowingLoadingState.value = false
                    }
                } else if (loginText.value.isEmpty()) {
                    toastMsg.value = "\"Մուտքանուն\" դաշտը չի կարող դատարկ լինել"
                    isShowingToast.value = true
                } else if (passwordText.value.isEmpty()) {
                    toastMsg.value = "\"Գաղտնաբառ \" դաշտը չի կարող դատարկ լինել"
                    isShowingToast.value = true
                }
            },
        )
        if (isShowingToast.value) {
            Toast.makeText(LocalContext.current, toastMsg.value, Toast.LENGTH_LONG).show()
        }
        LoadingPopUp(isShowDialog = isShowingLoadingState , appTheme)
    }
}

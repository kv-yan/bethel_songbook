package ru.betel.app.view_model.settings

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.betel.app.ui.theme.normalItemDefaultTextSize
import ru.betel.app.ui.theme.smallItemDefaultTextSize
import ru.betel.app.ui.theme.textFieldItemDefaultTextSize
import ru.betel.data.operators.plus
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize
import ru.betel.domain.useCase.auth.CheckUserLoginStatusUseCase
import ru.betel.domain.useCase.auth.LogInUseCase
import ru.betel.domain.useCase.auth.LogOutUseCase
import ru.betel.domain.useCase.network.GetNetworkStateUseCase
import ru.betel.domain.useCase.theme.GetThemeListUseCase
import ru.betel.domain.useCase.theme.GetThemeUseCase
import ru.betel.domain.useCase.theme.SetThemeUseCase

class SettingViewModel(
    val sharedPerf: SharedPreferences,
    private val logInUseCase: LogInUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val checkUserLoginStatusUseCase: CheckUserLoginStatusUseCase,
    getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val getThemeListUseCase: GetThemeListUseCase
) : ViewModel() {

    private val _appTheme: MutableState<AppTheme> = getThemeMode()
    val appTheme = _appTheme

    private fun getThemeMode(): MutableState<AppTheme> {

        val mode = getThemeUseCase.execute()

        return mutableStateOf(getThemeUseCase.execute())
    }

    fun setTheme(themeIndex: Int, themeMode: AppTheme) {
        setThemeUseCase.execute(themeIndex)
        _appTheme.value = themeMode
    }

    val modes = getThemeListUseCase.execute()
    var checkUserLoginStatus = mutableStateOf(FirebaseAuth.getInstance().currentUser != null)
    private var additionTextSize = mutableFloatStateOf(sharedPerf.getFloat("textSize", 0f))

    private val _networkState = mutableStateOf(getNetworkStateUseCase.execute())
    val networkState = _networkState

    val songbookTextSize: SongbookTextSize
        get() = SongbookTextSize(
            normalItemDefaultTextSize + additionTextSize.floatValue,
            smallItemDefaultTextSize + additionTextSize.floatValue,
            textFieldItemDefaultTextSize + additionTextSize.floatValue
        )

    private fun updateTextSize(newSize: Float) {
        sharedPerf.edit().putFloat("textSize", newSize).apply()
        additionTextSize.floatValue = sharedPerf.getFloat("textSize", 0f)
    }

    fun increaseTextSize() {
        val currentSize = additionTextSize.floatValue
        val newSize = currentSize + 1f
        if (currentSize <= 12f) {
            if (newSize >= 0f) {
                updateTextSize(newSize)
            }
        }
    }

    fun decreaseTextSize() {
        val currentSize = additionTextSize.floatValue
        val newSize = currentSize - 1f
        if (newSize >= 0f) {
            updateTextSize(newSize)
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = logInUseCase.execute(email, password)

        }
    }


    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = logOutUseCase.execute()
        }
    }
}


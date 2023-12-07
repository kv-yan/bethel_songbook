package ru.betel.app.view_model.settings

import android.content.SharedPreferences
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
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
import ru.betel.domain.model.ui.SongbookTextSize
import ru.betel.domain.model.ui.ThemeMode
import ru.betel.domain.useCase.auth.CheckUserLoginStatusUseCase
import ru.betel.domain.useCase.auth.LogInUseCase
import ru.betel.domain.useCase.auth.LogOutUseCase
import ru.betel.domain.useCase.network.GetNetworkStateUseCase

class SettingViewModel(
    val sharedPerf: SharedPreferences,
    private val logInUseCase: LogInUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val checkUserLoginStatusUseCase: CheckUserLoginStatusUseCase,
    getNetworkStateUseCase: GetNetworkStateUseCase,
) : ViewModel() {
    val modes = mutableStateListOf(ThemeMode.Light, ThemeMode.LightGray, ThemeMode.DarkGray)
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
        println("settings ::updated song")
    }

    fun increaseTextSize() {
        val currentSize = additionTextSize.floatValue
        val newSize = currentSize + 1f
        println("settings :: increaseTextSize :: check plus text size :: $newSize :: newSize >= 0f ${(newSize >= 0f)}")
        if (newSize >= 0f) {
            updateTextSize(newSize)
            println("settings :: increaseTextSize :: currentSize = ${additionTextSize.floatValue}")
            println("-------------------------------------------------------------------")
        }
    }

    fun decreaseTextSize() {
        val currentSize = additionTextSize.floatValue
        val newSize = currentSize - 1f
        println("settings :: decreaseTextSize :: check minus text size :: $newSize :: newSize >= 0f ${(newSize >= 0f)}")
        if (newSize >= 0f) {
            updateTextSize(newSize)
            println("settings :: decreaseTextSize :: currentSize = ${additionTextSize.floatValue}")
            println("settings :: -------------------------------------------------------------------")
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = logInUseCase.execute(email, password)
            if (result.isSuccess) {
                println("heyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
            } else {
                // TODO: handle can't sign in logick
                println("nooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
            }
        }
    }


    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = logOutUseCase.execute()
        }
    }
}


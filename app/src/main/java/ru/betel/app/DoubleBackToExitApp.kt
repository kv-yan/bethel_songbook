package ru.betel.app

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun DoubleBackToExitApp() {
    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Use LaunchedEffect to reset the flag after a delay
    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            delay(2000) // 2 seconds to reset the flag
            backPressedOnce = false
        }
    }

    BackHandler {
        if (backPressedOnce) {
            (context as? MainActivity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }
}
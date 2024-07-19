package ru.betel.app.ui.widgets.loading_anim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.betel.domain.model.ui.AppTheme

@Composable
fun LoadingScreen(appTheme: AppTheme = AppTheme.GRAY) {
    Column(
        modifier = Modifier.fillMaxSize().background(appTheme.screenBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircleLoadingAnimation(circleColor = appTheme.primaryIconColor)
    }
}

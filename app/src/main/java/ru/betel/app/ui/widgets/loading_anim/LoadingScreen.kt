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
fun LoadingScreen(appTheme: AppTheme) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircleLoadingAnimation(circleColor = appTheme.primaryIconColor)
    }
}

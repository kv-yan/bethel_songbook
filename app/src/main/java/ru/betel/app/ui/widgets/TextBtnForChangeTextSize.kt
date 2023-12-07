package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextBtnForChangeTextSize(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope) -> Unit,
) {
    Surface(color = Color.Gray, shape = RoundedCornerShape(12.dp)) {
        Box(
            modifier = modifier.height(40.dp),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

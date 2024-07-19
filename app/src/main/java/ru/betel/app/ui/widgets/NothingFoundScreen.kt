package ru.betel.app.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.betel.app.R
import ru.betel.domain.model.ui.AppTheme

@Composable
fun NothingFoundScreen(appTheme: AppTheme) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().background(appTheme.screenBackgroundColor)
    ) {
        Text(text = "Արդյունք չգտնվեց!", color = appTheme.primaryTextColor)
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_cant_find_item),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
    }
}

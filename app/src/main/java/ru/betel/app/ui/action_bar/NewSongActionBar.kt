package ru.betel.app.ui.action_bar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.domain.model.ui.Screens
import ru.betel.app.R

@Composable
fun NewSongActionBar(navController: NavController) {
    Surface(
        color = actionBarColor, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_go_back),
                    contentDescription = "menu_btn",
                    tint = Color.White,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
            }

            Text(
                text = "Նոր երգ ", style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                )
            )
        }
    }
}

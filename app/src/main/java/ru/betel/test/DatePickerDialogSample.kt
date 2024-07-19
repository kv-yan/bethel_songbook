@file:OptIn(ExperimentalMaterial3Api::class)

package ru.betel.test


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.betel.app.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerScreen(dayState: MutableState<String>) {
    val dateTime = LocalDateTime.now()

    val datePickerState = remember {
        DatePickerState(
            yearRange = 2019..2050,
            initialDisplayMode = DisplayMode.Input,
            initialDisplayedMonthMillis = null,
            initialSelectedDateMillis = null
        )
    }

    val selectedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DatePicker(state = datePickerState)

        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { millis ->
                selectedDate.value =
                    Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
            }
        }

        selectedDate.value?.let { date ->
            dayState.value = date.toString()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayPickerDialog(isShowing: MutableState<Boolean>, dayState: MutableState<String>) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { isShowing.value = true },
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(0.7f), text = dayState.value, style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                fontWeight = FontWeight(400),
                color = Color(0xFF111111),
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_drop_down),
            contentDescription = null,
            modifier = Modifier.size(width = 10.dp, height = 7.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
    if (isShowing.value) {
        Surface(color = Color.White) {
            AlertDialog(onDismissRequest = { isShowing.value = false }) {
                DatePickerScreen(dayState)
            }
        }
    }
}

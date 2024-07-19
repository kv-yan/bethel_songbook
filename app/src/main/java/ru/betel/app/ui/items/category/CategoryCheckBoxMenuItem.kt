package ru.betel.app.ui.items.category


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongsCategory

@Composable
fun CategoryCheckBoxMenuItem(
    appTheme: AppTheme,
    isCheckedItem: MutableState<Boolean>,
    category: SongsCategory,
    isCheckBox: Boolean = true,
    onClick: (category: SongsCategory, isSelected: Boolean) -> Unit,
) {
    Row(
        Modifier
            .clickable {
                onClick(category, isCheckedItem.value)
            }
            .fillMaxWidth()
            .padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(18.dp))

        if (isCheckBox) {
            Checkbox(
                checked = isCheckedItem.value,
                modifier = Modifier.size(1.dp),
                onCheckedChange = { onClick(category, isCheckedItem.value) },
                colors = CheckboxDefaults.colors(
                    checkedColor = appTheme.primaryTextColor,
                    checkmarkColor = appTheme.fieldBackgroundColor,
                    uncheckedColor = appTheme.secondaryTextColor
                )
            )
        } else {
            RadioButton(
                selected = isCheckedItem.value,
                modifier = Modifier.size(1.dp),
                onClick = { onClick(category, isCheckedItem.value) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = appTheme.primaryTextColor,
                    unselectedColor = appTheme.secondaryTextColor
                )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category.title, style = TextStyle(
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                fontWeight = FontWeight(400),
                color = if (isCheckedItem.value) appTheme.primaryTextColor else appTheme.secondaryTextColor,
            ), modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
    }
}
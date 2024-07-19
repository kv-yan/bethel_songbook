package ru.betel.app.ui.widgets.dropdown_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.items.category.CategoryCheckBoxMenuItem
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongsCategory

/*

@Composable
fun CategoryDropDownMenuWithCheckBox(
    selectedCategory: MutableState<String>,
    isGlorifying: MutableState<Boolean>,
    isWorship: MutableState<Boolean>,
    isGift: MutableState<Boolean>,
    isFromSongbook: MutableState<Boolean>, modifier: Modifier,
) {
    val expanded = remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = fieldBg,
        modifier = modifier
            .height(38.dp)
            .widthIn(min = 125.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded.value = true },
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = selectedCategory.value.ifEmpty { "Կատեգորիա" },
                style = TextStyle(
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
            DropdownMenu(
                modifier = Modifier.widthIn(120.dp),
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {

                CategoryCheckBoxMenuItem(
                    category = SongsCategory.GLORIFYING,
                    isCheckedItem = isGlorifying,
                    isCheckBox = false
                ) { category, _ ->

                    isGlorifying.value = !isGlorifying.value
                    isWorship.value = !isGlorifying.value

                    if (isGlorifying.value) {
                        selectedCategory.value += if (selectedCategory.value.isEmpty()) category else ",$category"
                    } else {
                        if (selectedCategory.value.contains(",$category")) selectedCategory.value -= ",$category"
                        else if (selectedCategory.value.contains(category)) selectedCategory.value -= category
                    }
                }

                CategoryCheckBoxMenuItem(
                    category = SongsCategory.WORSHIP, isCheckedItem = isWorship, isCheckBox = false
                ) { category, _ ->

                    isGlorifying.value = !isGlorifying.value
                    isWorship.value = !isGlorifying.value

                    if ( isWorship.value) {
                        selectedCategory.value += if (selectedCategory.value.isEmpty()) category else ",$category"
                    } else {
                        if (selectedCategory.value.contains(",$category")) selectedCategory.value -= ",$category"
                        else if (selectedCategory.value.contains(category)) selectedCategory.value -= category
                    }

                }

                CategoryCheckBoxMenuItem(
                    category = SongsCategory.GIFT, isCheckedItem = isGift
                ) { category, _ ->
                    isGift.value = !isGift.value
                    if (isGift.value) {
                        selectedCategory.value += if (selectedCategory.value.isEmpty()) category else ",$category"
                    } else {
                        if (selectedCategory.value.contains(" ,$category")) selectedCategory.value -= " ,$category"
                        else if (selectedCategory.value.contains(category)) selectedCategory.value -= category
                    }
                }

                CategoryCheckBoxMenuItem(
                    category = SongsCategory.FROM_SONGBOOK, isCheckedItem = isFromSongbook
                ) { category, _ ->
                    isFromSongbook.value = !isFromSongbook.value

                    if (isFromSongbook.value) {
                        selectedCategory.value += if (selectedCategory.value.isEmpty()) category else ",$category"
                    } else {
                        if (selectedCategory.value.contains(",$category")) selectedCategory.value -= ",$category"
                        else if (selectedCategory.value.contains(category)) selectedCategory.value -= category
                    }

                }

            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}
*/

@Composable
fun CategoryDropDownMenuWithCheckBox(
    selectedCategory: MutableState<String>,
    categories: List<SongsCategory>,
    categoryStates: List<MutableState<Boolean>>,
    modifier: Modifier,
    appTheme: AppTheme,
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedCategoriesSet = remember { mutableStateOf(mutableSetOf<String>()) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = appTheme.fieldBackgroundColor,
        modifier = modifier
            .height(38.dp)
            .widthIn(min = 125.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded.value = true },
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = selectedCategory.value.ifEmpty { "Կատեգորիա" },
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                    fontWeight = FontWeight(400),
                    color = appTheme.primaryTextColor,
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = null,
                tint = appTheme.primaryTextColor,
                modifier = Modifier.size(width = 10.dp, height = 7.dp)
            )


            DropdownMenu(
                modifier = Modifier
                    .widthIn(120.dp)
                    .background(appTheme.fieldBackgroundColor),
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                for (index in categories.indices) {
                    CategoryCheckBoxMenuItem(
                        appTheme = appTheme,
                        category = categories[index],
                        isCheckedItem = categoryStates[index]
                    ) { category, isChecked ->
                        categoryStates[index].value = !categoryStates[index].value
                        updateSelectedCategory(selectedCategoriesSet, category, isChecked)
                        selectedCategory.value = selectedCategoriesSet.value.joinToString(",")
                    }
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

fun updateSelectedCategory(
    selectedCategoriesSet: MutableState<MutableSet<String>>,
    category: SongsCategory,
    isChecked: Boolean
) {
    val categoryText = when (category) {
        SongsCategory.GLORIFYING -> "Փառաբանություն"
        SongsCategory.WORSHIP -> "Երկրպագություն"
        SongsCategory.GIFT -> "Ընծա"
        SongsCategory.FROM_SONGBOOK -> "Երգարանային"
        SongsCategory.ALL -> ""
    }
    if (isChecked) {
        selectedCategoriesSet.value.remove(categoryText)
    } else {
        selectedCategoriesSet.value.add(categoryText)
    }
}
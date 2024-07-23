package ru.betel.app.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.items.song.SongItemWithWords
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongCategory
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize


@Composable
fun SongCategoryHeader(appTheme: AppTheme,header: String) {
    Text(
        text = header,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.mardoto_regular)),
            fontWeight = FontWeight.W700,
            color = appTheme.secondaryTextColor,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategorizedLazyColumn(
    appTheme: AppTheme ,
    categories: List<SongCategory>,
    textSize: SongbookTextSize,
    onEditClick: (Song) -> Unit,
    onShareClick: (Song) -> Unit,
    onDeleteClick: (Song) -> Unit,
    onItemClick: (Song) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        categories.forEach { category ->
            stickyHeader {
                Surface(
                    color = appTheme.screenBackgroundColor.copy(alpha = 0.95f),
                    elevation = 3.dp,
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SongCategoryHeader(appTheme = appTheme,header = category.charName)
                }
            }

            items(category.items, key = { it.id }) { song ->
                SongItemWithWords(
                    isEnableLongPress = true,
                    appTheme = appTheme,
                    item = song,
                    textSize = textSize,
                    onEditClick = onEditClick,
                    onShareClick = onShareClick,
                    onDeleteClick = onDeleteClick,
                    onItemClick = {
                        onItemClick(song)
                    }
                )
            }
        }
    }
}

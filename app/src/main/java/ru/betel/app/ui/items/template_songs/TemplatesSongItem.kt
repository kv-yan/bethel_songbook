package ru.betel.app.ui.items.template_songs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun TemplatesSongItem(
    song: Song, textSize: SongbookTextSize, remainingQuantity: String, songViewModel: SongViewModel
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(song) {
        songViewModel.selectedSong.value = song
    }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = song.title,
                style = TextStyle(
                    fontSize = textSize.normalItemDefaultTextSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(700),
                    color = actionBarColor,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )

            Text(
                text = remainingQuantity,
                style = TextStyle(
                    fontSize = textSize.normalItemDefaultTextSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(700),
                    color = textFieldPlaceholder,
                ),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )

        }
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${song.temp} / ${song.tonality}",
                style = TextStyle(
                    fontSize = textSize.normalItemDefaultTextSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(700),
                    color = textFieldPlaceholder,
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = song.words,
            style = TextStyle(
                fontSize = textSize.normalItemDefaultTextSize,
                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                fontWeight = FontWeight(400),
                color = Color.Black.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
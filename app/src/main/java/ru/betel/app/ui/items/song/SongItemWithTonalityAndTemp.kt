package ru.betel.app.ui.items.song

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun SongItemWithTonalityAndTemp(
    item: Song, index: Int,
    isLastItem: Boolean,
    fontSize: SongbookTextSize,
    onItemClick: (item: Song) -> Unit,
) {

    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 35.dp)
            .background(Color.White)
            .clickable {
                onItemClick(item)
            }, verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))/*
                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$index.", style = TextStyle(
                            fontSize = fontSize.normalItemDefaultTextSize,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color.Black
                        ), modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = item.title, maxLines = 1, style = TextStyle(
                            fontSize = fontSize.normalItemDefaultTextSize,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color.Black
                        ), modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(start = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "130 | ${item.tonality}", style = TextStyle(
                                fontSize = fontSize.normalItemDefaultTextSize,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color.Black,
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
        */

        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            // Index on the left
            Text(
                text = "$index.", style = TextStyle(
                    fontSize = fontSize.normalItemDefaultTextSize,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color.Black
                ), modifier = Modifier.padding(start = 8.dp)
            )

            // Centered title with remaining width
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = item.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = fontSize.normalItemDefaultTextSize,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }

            // Tonality and date on the right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(0.4f) // Adjust the width as needed
            ) {
                Text(
                    text = "130 | ${item.tonality}", style = TextStyle(
                        fontSize = fontSize.normalItemDefaultTextSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(
            Modifier.height(8.dp)
        )
        if (!isLastItem) {
            Divider(Modifier.fillMaxWidth(), 1.dp, color = songDividerColor)
        }
    }
}
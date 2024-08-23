package ru.betel.app.ui.items.song

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AppTheme


@Composable
fun SongItemWithDeleteBtn(
    appTheme: AppTheme,
    item: Song,
    index: Int,
    isLastItem: Boolean,
    onDeleteItemClick: (item: Song) -> Unit,
    onTonalityTempItemLongPres: (item: Song) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 35.dp)
            .background(appTheme.screenBackgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${index}.${item.title}",
                maxLines = 1,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(400),
                    color = appTheme.primaryTextColor
                ),
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxWidth()
            ) {
                Text(text = if (item.isUsingSoundTrack) {
                    "(ֆ)"
                } else "${item.tonality} | ${item.temp}", style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(400),
                    color = appTheme.primaryTextColor,
                ), modifier = Modifier
                    .padding(end = 8.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = { onTonalityTempItemLongPres(item) },
                            onTap = { onTonalityTempItemLongPres(item) })
                    })

                Icon(painter = painterResource(id = R.drawable.ic_delete),
                    tint = appTheme.secondaryTextColor,
                    contentDescription = "Delete this song",
                    modifier = Modifier
                        .sizeIn(minWidth = 12.dp, minHeight = 12.dp)
                        .clickable {
                            onDeleteItemClick(item)
                        })
            }
        }
        Spacer(Modifier.height(8.dp))
        if (!isLastItem) {
            Divider(
                Modifier.fillMaxWidth(), 1.dp, color = appTheme.dividerColor
            )
        }
    }
}

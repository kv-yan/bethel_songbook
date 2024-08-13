package ru.betel.app.ui.items.song

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import ru.betel.app.R
import ru.betel.data.extensions.getWordsFirst2Lines
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize


@Composable
fun FavoriteSongItemWithWords(
    appTheme: AppTheme,
    item: Song,
    textSize: SongbookTextSize,
    onItemClick: () -> Unit,
    onEditClick: (Song) -> Unit,
    onShareClick: (Song) -> Unit,
    onDeleteSong: (Song) -> Unit,
    onRemoveFavSong: (Song) -> Unit,
) {
    val isShowAdditionBtn = remember { mutableStateOf(false) }
    val isAdminModeOn = FirebaseAuth.getInstance().currentUser != null
    val horizontalScrollState = rememberScrollState()


    Column(modifier = Modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                isShowAdditionBtn.value = !isShowAdditionBtn.value
            }, onTap = {
                onItemClick()
            })
        }
        .background(color = if (isShowAdditionBtn.value) appTheme.fieldBackgroundColor else appTheme.screenBackgroundColor)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .horizontalScroll(horizontalScrollState)
                .wrapContentWidth()
                .widthIn(max = 370.dp, min = 200.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = {
                onRemoveFavSong(item)
            }, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = appTheme.primaryTextColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 20.dp)
            ) {
                Text(
                    text = item.title, style = TextStyle(
                        fontSize = textSize.normalItemDefaultTextSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(500),
                        color = appTheme.primaryTextColor,
                    )
                )
                Text(
                    text = item.getWordsFirst2Lines(), style = TextStyle(
                        fontSize = textSize.smallItemDefaultTextSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(400),
                        color = appTheme.secondaryTextColor
                    ), modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            AnimatedVisibility(
                visible = isShowAdditionBtn.value, Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    if (isAdminModeOn) {
                        IconButton(
                            onClick = { onEditClick(item) }, modifier = Modifier.size(20.dp, 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = appTheme.secondaryTextColor,
                                modifier = Modifier.size(15.dp, 20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    IconButton(
                        onClick = { onShareClick(item) }, modifier = Modifier.size(20.dp, 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = appTheme.secondaryTextColor,
                            modifier = Modifier.size(15.dp, 20.dp)
                        )
                    }

                    if (isAdminModeOn) {
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = { onDeleteSong(item) }, modifier = Modifier.size(20.dp, 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = appTheme.secondaryTextColor,
                                modifier = Modifier.size(17.dp)
                            )
                        }
                    }
                }
            }
        }
        Divider(
            color = appTheme.dividerColor, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp)
        )
    }
}
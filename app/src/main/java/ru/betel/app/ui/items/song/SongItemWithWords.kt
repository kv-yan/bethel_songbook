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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import ru.betel.app.R
import ru.betel.data.extensions.getWordsFirst2Lines
import ru.betel.data.reopsitory.network.NetworkUtilsImpl
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize


@Composable
fun SongItemWithWords(
    isEnableLongPress: Boolean = true,
    appTheme: AppTheme,
    item: Song,
    textSize: SongbookTextSize,
    onEditClick: (Song) -> Unit,
    onShareClick: (Song) -> Unit,
    onDeleteClick: (Song) -> Unit,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    val networkUtils = remember { NetworkUtilsImpl(context) }
    val isShowAdditionBtn = remember { mutableStateOf(false) }

    val horizontalScrollState = rememberScrollState()
    val isConnected = remember { networkUtils.isConnectedNetwork() }

    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                if (isConnected) {
                    isShowAdditionBtn.value = !isShowAdditionBtn.value
                }
            }, onTap = { onItemClick() })
        }
        .background(color = if (isShowAdditionBtn.value) appTheme.fieldBackgroundColor else appTheme.screenBackgroundColor)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState)
                .widthIn(min = 0.dp, max = 500.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)) {
                Text(
                    text = item.title, style = TextStyle(
                        fontSize = textSize.normalItemDefaultTextSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight.W500,
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


            if (isConnected && isEnableLongPress) {
                AnimatedVisibility(
                    visible = isShowAdditionBtn.value,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            IconButton(
                                onClick = { onEditClick(item) },
                                modifier = Modifier.size(20.dp, 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = appTheme.primaryIconColor,
                                    modifier = Modifier.size(15.dp, 20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = { onShareClick(item) }, modifier = Modifier.size(20.dp, 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null,
                                tint = appTheme.primaryIconColor,
                                modifier = Modifier.size(15.dp, 20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = {  }, modifier = Modifier.size(20.dp, 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_bookmark),
                                contentDescription = null,
                                tint = appTheme.primaryIconColor,
                                modifier = Modifier.size(15.dp, 20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            IconButton(
                                onClick = { onDeleteClick(item) },
                                modifier = Modifier.size(20.dp, 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = appTheme.primaryIconColor,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
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

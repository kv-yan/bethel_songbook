package ru.betel.app.ui.items.template

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.data.extensions.getSongsTitle
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun SongTemplateColumItem(
    appTheme: AppTheme,
    template: SongTemplate,
    textSize: SongbookTextSize,
    onCLick: () -> Unit,
) {
    var isShowingTemplateDetails by rememberSaveable { mutableStateOf(false) }

    Surface(
        color = appTheme.fieldBackgroundColor,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onCLick() },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = template.performerName, style = TextStyle(
                        fontSize = textSize.normalItemDefaultTextSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(500),
                        color = appTheme.primaryTextColor,
                    ), modifier = Modifier.fillMaxWidth(0.4f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${template.weekday.substring(0..2)}. | ${template.createDate}",
                        style = TextStyle(
                            fontSize = if (textSize.normalItemDefaultTextSize > 18.sp) 16.sp else textSize.smallItemDefaultTextSize,
                            fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                            fontWeight = FontWeight(400),
                            color = appTheme.secondaryTextColor,
                        ),
                    )

                    IconButton(onClick = {
                        isShowingTemplateDetails = !isShowingTemplateDetails
                    }, modifier = Modifier.size(24.dp)) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            if (isShowingTemplateDetails) Icons.Filled.KeyboardArrowUp
                            else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = appTheme.secondaryTextColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            AnimatedVisibility(visible = isShowingTemplateDetails) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(1.dp))
                    Divider(color = songDividerColor, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = template.getSongsTitle(),
                        lineHeight = 24.sp,
                        style = TextStyle(
                            fontSize = textSize.normalItemDefaultTextSize,
                            fontWeight = FontWeight(400),
                            color = appTheme.secondaryTextColor,
                        ),
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

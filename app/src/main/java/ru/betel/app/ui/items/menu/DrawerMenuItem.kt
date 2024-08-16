package ru.betel.app.ui.items.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.MenuItem

@Composable
fun DrawerMenuItem(
    item: MenuItem, appTheme: AppTheme, menuItemClick: () -> Unit,
) {
    Box(modifier = Modifier.padding(horizontal = 11.dp)) {
        Surface(
            color = if (item.isSelected.value) appTheme.drawerFieldSelectedColor else appTheme.drawerFieldUnselectedColor,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(modifier = Modifier.clickable {
                menuItemClick()
            }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.padding(start = 9.dp))
                    Icon(
                        painter = painterResource(id = if (item.isSelected.value) item.activeIcon else item.passiveIcon),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null,
                        tint = if (item.isSelected.value) appTheme.drawerSelectedIconColor else appTheme.drawerUnselectedIconColor
                    )
                    Text(
                        text = item.title,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp,
                        color = if (item.isSelected.value) appTheme.drawerSelectedIconColor else appTheme.drawerUnselectedIconColor,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}
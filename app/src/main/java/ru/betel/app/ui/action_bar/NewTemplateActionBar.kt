package ru.betel.app.ui.action_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.betel.app.R
import ru.betel.app.ui.widgets.dropdown_menu.TemplateModeTypeDropdownMenu
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.AppTheme

@Composable
fun NewTemplateActionBar(
    appTheme: AppTheme,
    navController: NavController,
    editViewModel: EditViewModel,
    templateViewModel: TemplateViewModel ,
    onResetClick: () -> Unit
) {
    val isSingleMode = templateViewModel.isSingleMode

    Surface(
        color = appTheme.actionBarColor, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                editViewModel.cleanFields()
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_go_back),
                    contentDescription = "menu_btn",
                    tint = appTheme.actionBarIconColor,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
            }

            Text(
                text = "Նոր Ցուցակ ", style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    color = appTheme.actionBarIconColor,
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TemplateModeTypeDropdownMenu(isSingleMode, appTheme)

                IconButton(onClick = { onResetClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reset),
                        tint = appTheme.actionBarIconColor,
                        contentDescription = null
                    )
                }

            }
        }
    }
}

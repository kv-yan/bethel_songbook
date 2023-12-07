package ru.betel.app.ui.widgets.dropdown_menu

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.betel.app.R
import ru.betel.app.ui.items.add_item.ActionBarAddNewItemDropdownItem
import ru.betel.domain.model.ui.AddItem
import ru.betel.domain.model.ui.Screens

@Composable
fun AddNewItemDropdownMenu(expanded: MutableState<Boolean>, navController: NavController,userStatus:Boolean) {
    Box {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(x = (-12).dp, y = 12.dp)
        ) {
            if (userStatus){
                ActionBarAddNewItemDropdownItem(
                    AddItem(
                        "Նոր երգ", R.drawable.ic_add_new_song
                    )
                ) {
                    navController.navigate(Screens.NEW_SONG_SCREEN.route)
                }
            }
            ActionBarAddNewItemDropdownItem(
                AddItem(
                    "Նոր ցուցակ", R.drawable.ic_add_new_template
                )
            ) { navController.navigate(Screens.NEW_TEMPLATE_SCREEN.route) }
        }
    }

}
package ru.betel.app.ui.widgets.dropdown_menu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ru.betel.app.ui.items.category.CategoryMenuItem
import ru.betel.app.ui.items.template_menu.TemplateMenuItem
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.TemplateType

@SuppressLint("UnrememberedMutableState")
@Composable
fun TemplateDropdownMenu(
    expanded: MutableState<Boolean>,
    selectedTemplateType: MutableState<TemplateType>,
    templateViewModel: TemplateViewModel,
) {
    val template = remember {
        listOf(
            TemplateType.MINE,
            TemplateType.ALL
        )
    }

    Box {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(x = 0.dp, y = 12.dp)
        ) {

            template.forEach { type ->
                TemplateMenuItem(
                    templateType = type,
                    isShowDropdownMenu = expanded
                ) {
                    templateViewModel.onTemplateTypeSelected(type)
                    template.forEach { templateType ->
                        templateType.isSelected = type == templateType
                    }
                    println("${type.title} :: ${type.isSelected} :: selectedTemplateType.value :: ${selectedTemplateType.value.title}")
                }
            }
        }
    }
}

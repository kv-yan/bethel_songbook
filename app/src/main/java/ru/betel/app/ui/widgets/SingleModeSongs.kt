/*
package ru.betel.app.ui.widgets


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.betel.app.ui.screens.new_template.AddNewSongCategory
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.Song

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleModeSongs(
    templateViewModel: TemplateViewModel,
    selectedCategory: MutableState<AddNewSongCategory>,
    selectedCategoryForAddNewSong: MutableLiveData<SnapshotStateList<Song>>,
    bottomSheetState: ModalBottomSheetState,
    isSingleMode: MutableState<Boolean>,
    scope: CoroutineScope,
) {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        templateViewModel.tempSingleModeSongs.value?.let {
            AddNewSongToTemplate(
                categoryTitle = AddNewSongCategory.SINGLE_MODE.title, categorySongs = it
            ) {
                selectedCategory.value = AddNewSongCategory.SINGLE_MODE
                selectedCategoryForAddNewSong.value = it
                scope.launch { bottomSheetState.show() }
            }
        }

    }
}*/

/*
package ru.betel.app.ui.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.betel.app.ui.screens.new_template.AddNewSongCategory
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AddSong

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategorizedSongs(
    templateViewModel: TemplateViewModel,
    selectedCategory: MutableState<AddNewSongCategory>,
    selectedCategoryForAddingNewSong: MutableLiveData<SnapshotStateList<Song>>,
    selectedCategoryBottomSheetAllSongs: MutableState<MutableList<AddSong>>,
    bottomSheetAllSongsForGlorifyingCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForWorshipCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForGiftCategory: State<MutableList<AddSong>>,
    bottomSheetState: ModalBottomSheetState,
    isSingleMode: MutableState<Boolean>,
    scope: CoroutineScope
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        templateViewModel.tempGlorifyingSongs.value?.let { glorifyingSongs ->
            AddNewSongToTemplate(
                categoryTitle = AddNewSongCategory.GLORIFYING.title, categorySongs = glorifyingSongs
            ) {
                selectedCategory.value = AddNewSongCategory.GLORIFYING
                selectedCategoryForAddingNewSong.value = glorifyingSongs
                selectedCategoryBottomSheetAllSongs.value =
                    bottomSheetAllSongsForGlorifyingCategory.value
                scope.launch { bottomSheetState.show() }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))


        templateViewModel.tempWorshipSongs.value?.let { worshipSongs ->
            AddNewSongToTemplate(
                categoryTitle = AddNewSongCategory.WORSHIP.title, categorySongs = worshipSongs
            ) {
                selectedCategory.value = AddNewSongCategory.WORSHIP
                selectedCategoryForAddingNewSong.value = worshipSongs
                selectedCategoryBottomSheetAllSongs.value =
                    bottomSheetAllSongsForWorshipCategory.value
                scope.launch { bottomSheetState.show() }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        templateViewModel.tempGiftSongs.value?.let { giftSongs ->
            AddNewSongToTemplate(
                categoryTitle = AddNewSongCategory.GIFT.title, categorySongs = giftSongs
            ) {
                selectedCategory.value = AddNewSongCategory.GIFT
                selectedCategoryForAddingNewSong.value = giftSongs
                selectedCategoryBottomSheetAllSongs.value = bottomSheetAllSongsForGiftCategory.value
                scope.launch { bottomSheetState.show() }
            }
        }
    }
}
*/

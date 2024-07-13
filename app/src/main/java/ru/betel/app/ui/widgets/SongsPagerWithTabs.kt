/*
package ru.betel.app.ui.widgets


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.betel.app.ui.screens.new_template.AddNewSongCategory
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AddSong

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun SongsPagerWithTabs(
    templateViewModel: TemplateViewModel,
    selectedCategory: MutableState<AddNewSongCategory>,
    selectedCategoryForAddNewSong: MutableLiveData<SnapshotStateList<Song>>,
    selectedCategoryBottomSheetAllSongs: MutableState<MutableList<AddSong>>,
    bottomSheetAllSongsForGlorifyingCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForWorshipCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForGiftCategory: State<MutableList<AddSong>>,
    bottomSheetState: ModalBottomSheetState,
    isSingleMode: MutableState<Boolean>,
    scope: CoroutineScope,
    tabRowItems: List<String>,
) {
    val pagerState = rememberPagerState()
    val horizontalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            modifier = Modifier
                .horizontalScroll(horizontalScrollState)
                .widthIn(200.dp, 400.dp),
            backgroundColor = Color.White,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = actionBarColor
                )
            },
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(modifier = Modifier.wrapContentWidth(),
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(text = item)
                    })
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            HorizontalPager(
                count = tabRowItems.size, state = pagerState, modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {

                    }

                    1 -> {
                    }
                }
            }
        }
    }
}*/

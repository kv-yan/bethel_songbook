package ru.betel.app.view_model.edit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.useCase.song.update.UpdateSongInFirebaseUseCase

class EditViewModel(
    private val updateSongInFirebaseUseCase: UpdateSongInFirebaseUseCase
) : ViewModel() {
    val currentSong = MutableStateFlow<Song>(
        Song(
            id = "Error",
            title = "Error",
            tonality = "Error",
            words = "Error",
            isGlorifyingSong = false,
            isWorshipSong = false,
            isGiftSong = false,
            isFromSongbookSong = false,
            temp = "Error"
        )

    )

    val singleTemplate = MutableStateFlow<SongTemplate>(
        SongTemplate(
            id = "Error",
            createDate = "Error",
            performerName = "Error",
            weekday = "Error",
            favorite = false,
            glorifyingSong = emptyList(),
            worshipSong = emptyList(),
            giftSong = emptyList()
        )
    )

    fun onSaveUpdates(current: Song, updatedSong: Song) {
        updateSongInFirebaseUseCase.execute(current, updatedSong)
    }

    fun onSaveUpdates(updatedTemplate: SongTemplate) {

    }
}
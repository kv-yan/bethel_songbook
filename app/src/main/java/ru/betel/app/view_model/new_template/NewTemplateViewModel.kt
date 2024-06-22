package ru.betel.app.view_model.new_template

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.useCase.template.set.SaveTemplateInFirebaseUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateToLocalUseCase
import java.text.SimpleDateFormat
import java.util.Calendar

class NewTemplateViewModel(
    private val saveTemplateToLocalUseCase: SaveTemplateToLocalUseCase,
    private val saveTemplateInFirebaseUseCase: SaveTemplateInFirebaseUseCase
) : ViewModel() {
    private val _tempCreateDate =
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time))
    val tempCreateDate = _tempCreateDate

    private val _tempPerformerName = mutableStateOf("")
    val tempPerformerName = _tempPerformerName

    private val _tempWeekday = mutableStateOf("")
    val tempWeekday = _tempWeekday

    private val _tempGlorifyingSongs: SnapshotStateList<Song> = mutableStateListOf()
    val tempGlorifyingSongs = _tempGlorifyingSongs

    private val _tempWorship : SnapshotStateList<Song> = mutableStateListOf()
    val tempWorshipSongs = _tempWorship

    private val _tempGift: SnapshotStateList<Song> = mutableStateListOf()
    val tempGiftSongs = _tempGift

    fun checkFields(): Result<Unit> {
        return if (tempPerformerName.value.isNotEmpty()) {
            if (tempWeekday.value.isNotEmpty()) {
                if (tempGlorifyingSongs.isNotEmpty()) {
                    if (tempWorshipSongs.isNotEmpty()) {
                        if (tempGiftSongs.isNotEmpty()) {
                            Result.success(Unit)
                        } else Result.failure(IllegalAccessError())
                    } else Result.failure(IllegalAccessError())
                } else Result.failure(IllegalAccessError())
            } else Result.failure(IllegalAccessError())
        } else Result.failure(IllegalAccessError())
    }



}
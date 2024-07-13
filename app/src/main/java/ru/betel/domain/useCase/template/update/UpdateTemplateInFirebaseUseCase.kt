package ru.betel.domain.useCase.template.update

import android.util.Log
import ru.betel.domain.model.SongTemplate

import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.constants.TEMPLATE_REF
import ru.betel.domain.model.Song

class UpdateTemplateInFirebaseUseCase {
    private val databaseRef = FirebaseDatabase.getInstance().getReference(TEMPLATE_REF)
    private val TAG = "UpdateTemplateInFirebaseUse"

    fun execute(template: SongTemplate, newTemplate: SongTemplate) {
        val templateId = template.id
        val templateRef = databaseRef.child(templateId)

        val updatedValues = mapOf(
            "createDate" to newTemplate.createDate,
            "performerName" to newTemplate.performerName,
            "weekday" to newTemplate.weekday,
            "favorite" to newTemplate.isSingleMode,
            "glorifyingSong" to newTemplate.glorifyingSong.map { it.toMap() },
            "worshipSong" to newTemplate.worshipSong.map { it.toMap() },
            "giftSong" to newTemplate.giftSong.map { it.toMap() }
        )

        templateRef.updateChildren(updatedValues) { error, ref ->
            if (error != null) {
                Log.e(TAG, "Failed to update template: ${error.message}")
            } else {
                Log.d(TAG, "Template updated successfully at $ref")
            }
        }
    }

    private fun Song.toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "title" to title,
            "tonality" to tonality,
            "words" to words,
            "temp" to temp,
            "glorifyingSong" to isGlorifyingSong,
            "worshipSong" to isWorshipSong,
            "giftSong" to isGiftSong,
            "fromSongbookSong" to isFromSongbookSong
        )
    }}

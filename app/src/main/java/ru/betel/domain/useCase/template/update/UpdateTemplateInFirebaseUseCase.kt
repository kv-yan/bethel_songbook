package ru.betel.domain.useCase.template.update

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import ru.betel.data.extensions.toMap
import ru.betel.domain.constants.TEMPLATE_REF
import ru.betel.domain.model.SongTemplate

class UpdateTemplateInFirebaseUseCase {
    private val databaseRef = FirebaseDatabase.getInstance().getReference(TEMPLATE_REF)
    private val TAG = "UpdateTemplateInFirebaseUse"

    fun execute(template: SongTemplate, newTemplate: SongTemplate) {
        val templateId = template.id
        val templateRef = databaseRef.child(templateId)

        val updatedValues = mapOf("createDate" to newTemplate.createDate,
            "performerName" to newTemplate.performerName,
            "weekday" to newTemplate.weekday,
            "favorite" to newTemplate.isSingleMode,
            "glorifyingSong" to newTemplate.glorifyingSong.map { it.toMap() },
            "worshipSong" to newTemplate.worshipSong.map { it.toMap() },
            "giftSong" to newTemplate.giftSong.map { it.toMap() },
            "singleModeSongs" to newTemplate.singleModeSongs.map { it.toMap() })

        templateRef.updateChildren(updatedValues) { error, ref ->
            if (error != null) {
                Log.e(TAG, "Failed to update template: ${error.message}")
            } else {
                Log.d(TAG, "Template updated successfully at $ref")
            }
        }
    }

}

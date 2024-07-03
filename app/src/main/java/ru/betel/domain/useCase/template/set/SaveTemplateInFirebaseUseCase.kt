package ru.betel.domain.useCase.template.set

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.SongTemplate

class SaveTemplateInFirebaseUseCase {
    private val TAG = "SaveTemplateInFirebaseUseCase"
    fun execute(template: SongTemplate): Boolean {
        return try {
            FirebaseDatabase.getInstance().getReference("SongsTemplate").push().setValue(template)
            true
        } catch (ex: Exception) {
            Log.e(TAG, "execute: ex :: ${ex.message}")
            false
        }
    }
}
package ru.betel.domain.useCase.template.set

import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.constants.TEMPLATE_REF
import ru.betel.domain.model.SongTemplate

class SaveTemplateInFirebaseUseCase {
    fun execute(template: SongTemplate): Boolean {
        return try {
            FirebaseDatabase.getInstance().getReference(TEMPLATE_REF).push().setValue(template)
            true
        } catch (ex: Exception) {
            false
        }
    }
}
package ru.betel.domain.useCase.notification

import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.notification.NotificationRepository

class SendNotificationToAllUsersUseCase(private val notificationRepository: NotificationRepository) {
    suspend fun execute(template: SongTemplate) {
        notificationRepository.sendNotification(template)
    }
}



package ru.betel.domain.repository.notification

import ru.betel.domain.model.SongTemplate

interface NotificationRepository {
    suspend fun sendNotification(template: SongTemplate) {}
}
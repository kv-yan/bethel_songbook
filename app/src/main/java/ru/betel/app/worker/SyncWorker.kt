package ru.betel.app.worker

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import ru.betel.data.database.AppDatabase
import ru.betel.data.database.MIGRATION_SONG_1_2
import ru.betel.data.database.MIGRATION_TEMPLATE_1_2
import ru.betel.data.reopsitory.song.get.firebase.GetSongFromFirebaseImpl
import ru.betel.domain.converters.toEntity

private const val TAG = "SyncWorker"

class SyncWorker(
    appContext: Context, workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val database = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "template"
        ).addMigrations(MIGRATION_TEMPLATE_1_2).addMigrations(MIGRATION_SONG_1_2).build()

        val dao = database.songDao()
        val songRepo = GetSongFromFirebaseImpl(FirebaseDatabase.getInstance())

        return try {
            val fbSongs = songRepo.getAllSongs()

            dao.cleareAllSongs()
            dao.insertSongs(fbSongs.map { it.toEntity() })

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "doWork: Error during sync", e)
            Result.failure()
        }
    }
}

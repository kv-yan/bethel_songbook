package ru.betel.data.share

import android.content.Context
import android.content.Intent
import ru.betel.data.extensions.getMessageForShare
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.share.ShareRepo

class ShareRepoImpl(private val context: Context) : ShareRepo {
    private val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
    }
    private val packageNames = listOf(
        "com.viber.voip", "com.whatsapp", "org.telegram.messenger", "com.facebook.orca"
    )

    override suspend fun shareSong(song: Song) {
        shareIntent.putExtra(Intent.EXTRA_TEXT, song.getMessageForShare())

        println("SHARE ::  ${song.getMessageForShare()}")

        val intentList = packageNames.mapNotNull { packageName ->
            context.packageManager.getLaunchIntentForPackage(packageName)?.let { intent ->
                intent.putExtra(Intent.EXTRA_TEXT, song.getMessageForShare())
                intent.type = "text/plain"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent
            }
        }

        val chooserIntent = Intent.createChooser(shareIntent, "Share via")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray())
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // add the flag here as well
        context.startActivity(chooserIntent)
    }

    override suspend fun shareSongTemplate(template: SongTemplate) {
        shareIntent.putExtra(Intent.EXTRA_TEXT, template.getMessageForShare())


        val intentList = packageNames.mapNotNull { packageName ->
            context.packageManager.getLaunchIntentForPackage(packageName)?.let { intent ->
                intent.putExtra(Intent.EXTRA_TEXT, template.getMessageForShare())
                intent.type = "text/plain"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent
            }
        }


        val chooserIntent = Intent.createChooser(shareIntent, "Share via")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray())
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // add the flag here as well
        context.startActivity(chooserIntent)

    }
}

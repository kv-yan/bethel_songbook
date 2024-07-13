package ru.betel.app.service

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.betel.app.R


class MyFirebaseMessagingService_2 : FirebaseMessagingService() {

    private  val TAG = "MyFirebaseMessagingServ"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e(TAG, "onMessageReceived: ***********************************************************************************************", )
        getFirebaseMessage(
            remoteMessage.notification!!.title, remoteMessage.notification!!
                .body
        )
    }
    private fun getFirebaseMessage(title: String?, msg: String?) {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "myFirebaseChannel")
                .setSmallIcon(R.drawable.des_1)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
        val manager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        manager.notify(101, builder.build())
    }

}



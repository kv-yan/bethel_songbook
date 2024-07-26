package ru.betel.app.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ru.betel.app.MainActivity;
import ru.betel.app.R;

public class FirebaseNotificationReceiver extends FirebaseMessagingService {
    private static final String TAG = "NOTIFICATION";
    private static final String DEFAULT_SOUND = "demo_sound";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getData().get("title");
        String msg = remoteMessage.getData().get("body");
        String sound = remoteMessage.getData().get("sound");

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + sound);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("template_id", remoteMessage.getData().get("template_id"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "bethel_firebase_notification_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(soundUri);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(101, builder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "bethel_firebase_notification_channel";
            CharSequence channelName = "Channel Name";
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo_sound);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel Description");
            channel.setSound(soundUri, new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build());

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "New token: " + token);
    }
}

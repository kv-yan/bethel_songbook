package ru.betel.app.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ru.betel.app.MainActivity;
import ru.betel.app.R;

public class NotificationReceiver extends FirebaseMessagingService {
    private static final String TAG = "NOTIFICATION";
    private static final String DEFAULT_SOUND = "demo_sound";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = "";
        String msg = "";
        String sound = DEFAULT_SOUND;

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            msg = remoteMessage.getNotification().getBody();
            String receivedSound = remoteMessage.getNotification().getSound();
            if (receivedSound != null) {
                sound = receivedSound;
            }
        } else if (!remoteMessage.getData().isEmpty()) {
            title = remoteMessage.getData().get("title");
            msg = remoteMessage.getData().get("body");
            String receivedSound = remoteMessage.getData().get("sound");
            if (receivedSound != null) {
                sound = receivedSound;
            }
        }

        Log.e(TAG, "onMessageReceived: (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) :: " + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Log.e(TAG, "onMessageReceived: (bethel_firebase_notification_channel) == null :: " + (notificationManager.getNotificationChannel("bethel_firebase_notification_channel") == null));
            if (notificationManager.getNotificationChannel("bethel_firebase_notification_channel") == null) {
                createNotificationChannel();
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("template_id", remoteMessage.getData().get("template_id"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo_sound);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "bethel_firebase_notification_channel").setSmallIcon(R.drawable.ic_notification).setContentTitle(title).setContentText(msg).setAutoCancel(true).setContentIntent(pendingIntent).setSound(soundUri);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.notify(101, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "bethel_firebase_notification_channel";
            CharSequence channelName = "Channel Name";
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo_sound);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel Description");
            channel.setSound(soundUri, new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_NOTIFICATION).build());

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

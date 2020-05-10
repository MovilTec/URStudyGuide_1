package com.example.urstudyguide_migration.Common.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.urstudyguide_migration.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public static final int FCM_REQUEST_CODE = 777;

    private String click_action, user_id, user_name;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        String notificationTitle = remoteMessage.getData().get("title");
        String notificationTitle = remoteMessage.getNotification().getTitle();
//        String notificationBody = remoteMessage.getData().get("body");
        String notificationBody = remoteMessage.getNotification().getBody();

        // HANDLING REMOTE DATA
        if (remoteMessage.getData().size() > 0) {
            click_action = remoteMessage.getData().get("click_action");
            user_id = remoteMessage.getData().get("user_id");
            user_name = remoteMessage.getData().get("user_name");
        }

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                //.setSmallIcon(R.drawable.notification_icon)
                .setSmallIcon(R.drawable.default_user_image_1)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification.Builder notification = new Notification.Builder(this);
                notification.setSmallIcon(R.drawable.default_user_image_1);
                notification.setContentTitle(notificationTitle);
                notification.setContentText(notificationBody);
                notification.setSound(alarmSound);

        Intent resultIntent;
        if (click_action != null)
            resultIntent = new Intent(click_action);
        else
            resultIntent = new Intent(remoteMessage.getNotification().getClickAction());

        //TODO:- Define necessary extra
        resultIntent.putExtra("user_id", user_id);
        resultIntent.putExtra("user_name", user_name);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this, FCM_REQUEST_CODE, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        int notificationId = (int) System.currentTimeMillis();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification.build());



    }
}

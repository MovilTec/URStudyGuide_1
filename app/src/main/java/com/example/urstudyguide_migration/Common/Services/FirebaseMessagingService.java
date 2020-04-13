package com.example.urstudyguide_migration.Common.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.urstudyguide_migration.MainActivity;
import com.example.urstudyguide_migration.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        String notificationTitle = remoteMessage.getData().get("title");
        String notificationTitle = remoteMessage.getNotification().getTitle();
//        String notificationBody = remoteMessage.getData().get("body");
        String notificationBody = remoteMessage.getNotification().getBody();

        String click_action = remoteMessage.getData().get("click_action");//getNotification().getClickAction();



        String from_user_id = remoteMessage.getData().get("from_user_id");

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                //.setSmallIcon(R.drawable.notification_icon)
                .setSmallIcon(R.drawable.default_user_image_1)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
//                .setSound(alarmSound)
//                .defaults |= alarmSound;
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //New Implementation?
        Notification.Builder notification = new Notification.Builder(this);
                notification.setSmallIcon(R.drawable.default_user_image_1);
                notification.setContentTitle(notificationTitle);
                notification.setContentText(notificationBody);
                notification.setSound(alarmSound);



        Intent resultIntent = new Intent(click_action);
        resultIntent.putExtra("user_id", from_user_id);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        int notificationId = (int) System.currentTimeMillis();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification.build());



    }

//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


}

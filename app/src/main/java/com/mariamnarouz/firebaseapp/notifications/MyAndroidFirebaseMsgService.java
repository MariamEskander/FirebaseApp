package com.mariamnarouz.firebaseapp.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.RemoteMessage;
import com.mariamnarouz.firebaseapp.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static android.R.attr.data;

/**
 * Created by Mariam.Narouz on 12/28/2017.
 */

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
         Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody()+
         "\n" + remoteMessage.getNotification().getTitle()
         +"\n" + remoteMessage.getData().toString());


        Map<String, String> notification = new HashMap<>();

        FirebaseNotificationManager notificationManager = FirebaseNotificationManager.getInstance(this);

        notification.put("notification-title", remoteMessage.getData().get("title"));
       notification.put("notification-message", remoteMessage.getData().get("message"));
        notificationManager.onNotification(notification);


    }




    private void createNotifications(int id, String title, String messageBody) {

        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notificationBuilder.build());

    }


}

package com.mariamnarouz.firebaseapp;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.data.model.Chat;
import com.mariamnarouz.firebaseapp.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mariam.Narouz on 12/28/2017.
 */

public class CheckForMessages  extends Service {
    private static final String TAG = "CheckForMessages";
    private User mainUser;
    private boolean f =  true;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainUser = Singleton.getInstance(this).getUser();
        Log.e(TAG, "onCreate");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = noteDataSnapshot.getValue(Chat.class);
                    assert chat != null;

                    if (chat.getReceiverId().equals(mainUser.getId()) || chat.getSenderId().equals(mainUser.getId()) ) {
                        //chats.add(chat);
                        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                        ComponentName componentInfo = taskInfo.get(0).topActivity;


                        if (!componentInfo.toString().contains("com.mariamnarouz.firebaseapp.ui.chat.chat_details")) {
                            createNotification(0, "You have a new Message", "new message");
                        }
                        Log.i("service", "chat msg added");
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("getChats", "onCancelled");
            }

        });

    }

    private void createNotification(int i, String title, String messageBody) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();

    }



}
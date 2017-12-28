package com.mariamnarouz.firebaseapp.notifications;

import android.content.Context;

import java.util.Map;

/**
 * Created by Mariam.Narouz on 12/28/2017.
 */

public class FirebaseNotificationManager  extends BaseManager implements NotificationMessageEVENT {

    private static FirebaseNotificationManager instance;
    private Context mContext;

    private FirebaseNotificationManager(Context context) {
        this.mContext = context;
    }

    public static FirebaseNotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseNotificationManager(context);
        }
        return instance;
    }

    public void onNotification(Map<String, String> notification) {
        updateObservers(NOTIFICATION,notification);
    }

}
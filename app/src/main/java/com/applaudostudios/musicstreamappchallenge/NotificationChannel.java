package com.applaudostudios.musicstreamappchallenge;

import android.app.Application;
import android.app.NotificationManager;
import android.os.Build;

import static com.applaudostudios.musicstreamappchallenge.Constants.CHANNEL_ID.PRIMARY_CHANNEL_ID;

public class NotificationChannel extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    private void createNotificationChannel() {
        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationChannel serviceChannel = new android.app.NotificationChannel(
                    PRIMARY_CHANNEL_ID,
                    "Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            // Creates a notification manager object.
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}

package com.applaudostudios.musicstreamappchallenge;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.applaudostudios.musicstreamappchallenge.Notification.CHANNEL_ID;

public class ForegroundService extends Service {

    // Called the first time the service is created
    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Triggered when the service starts
    // Called every time startService is called in the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input  = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        // Mandatory for notification in Android Oreo or higher
        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    // Called when the service stops
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Only needed for bound bound services (this case is a started service)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

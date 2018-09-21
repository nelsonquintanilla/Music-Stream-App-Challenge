package com.applaudostudios.musicstreamappchallenge;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


import java.io.IOException;

import static com.applaudostudios.musicstreamappchallenge.Notification.CHANNEL_ID;

public class ForegroundService extends Service implements MediaPlayer.OnPreparedListener {

    MediaPlayer mMediaPlayer = null;

    // Called the first time the service is created
    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Triggered when the service starts
    // Called every time startService is called in the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {

            String input = intent.getStringExtra("inputExtra");

            String url = "http://us5.internet-radio.com:8110/listen.pls&t=.m3u";
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // Because the file I am referencing might not exist.
            try {
                mMediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread


            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

            // DELETE THIS TOAST ----------
            Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
            broadcastIntent.putExtra("toastmessage", input);
            PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                    0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Mandatory for notification in Android Oreo or higher
            android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("You're listening to")
                    .setContentText(input)
                    .setSmallIcon(R.drawable.ic_android)
                    .setColor(Color.BLUE)
                    .setContentIntent(pendingIntent)
                    .addAction(R.mipmap.ic_launcher, "Play", actionIntent)
                    .addAction(R.mipmap.ic_launcher, "Pause", actionIntent)
                    .build();

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);

        } else if (intent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {


        }
        return START_NOT_STICKY;

    }

    // Called when the service stops
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Only needed for bound services (this case is a started service)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }
}

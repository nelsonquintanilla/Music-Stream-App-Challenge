package com.applaudostudios.musicstreamappchallenge;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


import java.io.IOException;

import static com.applaudostudios.musicstreamappchallenge.Notification.CHANNEL_ID;

public class ForegroundService extends Service implements MediaPlayer.OnPreparedListener {
    MediaPlayer mMediaPlayer = null;
    boolean mark;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    // Called the first time the service is created
    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        String url = "http://us5.internet-radio.com:8110/listen.pls&t=.m3u";
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // Because the file I am referencing might not exist.
        try {
            mMediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mark = true;
    }

    // Triggered when the service starts
    // Called every time startService is called in the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {

            // The boolean variable 'mark' is to make sure the Media Player will prepare itself
            // just the first time after it is created. After a call to the pause() method, it wont
            // prepare itself again, instead it will call the start() method to resume.
            if(mark){
                mMediaPlayer.prepareAsync(); // prepare async to not block main thread
                mMediaPlayer.setOnPreparedListener(this);
            } else {
                onPrepared(mMediaPlayer);
            }

            String input = intent.getStringExtra("inputExtra");
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
            mMediaPlayer.pause();
            mark = false;

        }
        return START_NOT_STICKY;

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        ForegroundService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ForegroundService.this;
        }
    }

    // Needed for bound services (In this case, a started and bound service)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Called when the service stops
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

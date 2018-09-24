package com.applaudostudios.musicstreamappchallenge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mPlayImageView;
    private boolean mIsPlaying;
//    private NotificationReceiver mNotificationReceiver = new NotificationReceiver();
    private ForegroundService mService;
    private boolean mBound = false;

    // Indicates whether the requested service exists and whether the client is permitted access
    // to it.
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, ForegroundService.class);
        // Flag: creates the service if it's not already alive.
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIsPlaying = false;
        mPlayImageView = findViewById(R.id.play_pause_button_image);
        mPlayImageView.setOnClickListener(this);

        ImageView mInfoImageView = findViewById(R.id.information_button_image);
        mInfoImageView.setOnClickListener(this);

//        // Registering the receiver
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constants.ACTION.ACTION_EXAMPLE);
//        registerReceiver(mNotificationReceiver, filter);
    }

    public void killService() {
        Intent killIntent = new Intent(this, ForegroundService.class);
        stopService(killIntent);
    }

    /**
     * Unregisters the receiver and stops the service when the app is being destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        killService();
//        unregisterReceiver(mNotificationReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_pause_button_image:
                if (mIsPlaying) {
                    Intent pauseIntent = new Intent(MainActivity.this, ForegroundService.class);
                    pauseIntent.setAction(Constants.ACTION.ACTION_PAUSE);
                    ContextCompat.startForegroundService(this, pauseIntent);
                    mPlayImageView.setImageResource(R.drawable.play_button_image);
                    mIsPlaying = false;
                } else {
                    Intent playIntent = new Intent(MainActivity.this, ForegroundService.class);
                    playIntent.setAction(Constants.ACTION.ACTION_PLAY);
                    ContextCompat.startForegroundService(this, playIntent);
                    mPlayImageView.setImageResource(R.drawable.pause_button_image);
                    mIsPlaying = true;
                }
                break;
            case R.id.information_button_image:
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            default:
                break;
        }
    }



    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ForegroundService.LocalBinder binder = (ForegroundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


}
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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String url;
    private ImageView playImageView;
    private boolean isPlaying;

    NotificationReceiver notificationReceiver = new NotificationReceiver();

//    ForegroundService mService;
//    boolean mBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.url_text_view);
        url = textView.getText().toString();

        playImageView = findViewById(R.id.play_button_image);
        isPlaying = false;

        playImageView.setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION.EXAMPLE_ACTION);
        registerReceiver(notificationReceiver, filter);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Bind to LocalService
//        Intent intent = new Intent(this, ForegroundService.class);
//        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//    }

    @Override
    public void onClick(View view) {
        if (isPlaying) {
            Intent pauseIntent = new Intent(MainActivity.this, ForegroundService.class);
            pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
            ContextCompat.startForegroundService(this, pauseIntent);
            playImageView.setImageResource(R.drawable.play_button_image);
            isPlaying = false;
        } else {
            Intent playIntent = new Intent(MainActivity.this, ForegroundService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            playIntent.putExtra("inputExtra", url);
            ContextCompat.startForegroundService(this, playIntent);
            playImageView.setImageResource(R.drawable.pause_button_image);
            isPlaying = true;
        }
    }

//    /** Defines callbacks for service binding, passed to bindService() */
//    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className,
//                                       IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            ForegroundService.LocalBinder binder = (ForegroundService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            mBound = false;
//        }
//    };

    public void killService() {
        Intent killIntent = new Intent(this, ForegroundService.class);
        stopService(killIntent);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        unbindService(mConnection);
//        mBound = false;
//    }

    // Stops the service only if the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        killService();
        unregisterReceiver(notificationReceiver);
    }



}
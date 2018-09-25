package com.applaudostudios.musicstreamappchallenge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ForegroundService.StateSwitcher {
    private ImageView mPlayImageView;
    private boolean mIsPlaying;
    private ForegroundService mService;

    /**
     * Indicates whether the requested service exists and whether the client is permitted access
     * to it.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, ForegroundService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Unbinds the service when the main activity goes to background.
     */
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // When the app is launched, it has set mIsPlaying to false so it can follow the correct
        // logic in the onClick method below. It also has the mPlayImageView variable set to the
        // play_button_image resource.
        mIsPlaying = false;
        mPlayImageView = findViewById(R.id.play_pause_button_image);
        mPlayImageView.setOnClickListener(this);


        ImageView mInfoImageView = findViewById(R.id.information_button_image);
        mInfoImageView.setOnClickListener(this);
    }

    // This method is called in the onDestroy method below to stop the service.
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
    }

    /**
     * Controls the state change of the play_pause button and sends the correct action within the
     * intent when it's passed in to the service.
     *
     * @param view either the play/pause button or the information button.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            ForegroundService.LocalBinder myBinder = (ForegroundService.LocalBinder) service;
            mService = myBinder.getService();
            mService.method(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };

    /**
     * Method of the interface StateSwitcher implemented in this class.
     * @param state either true or false
     */
    @Override
    public void switcher(boolean state) {
        if (mService.mState) {
            mPlayImageView.setImageResource(R.drawable.play_button_image);
            mIsPlaying = false;
        } else {
            mPlayImageView.setImageResource(R.drawable.pause_button_image);
            mIsPlaying = true;
        }
    }

}
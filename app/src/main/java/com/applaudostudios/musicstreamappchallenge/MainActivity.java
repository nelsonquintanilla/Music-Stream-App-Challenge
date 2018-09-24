package com.applaudostudios.musicstreamappchallenge;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView playImageView;
    private ImageView mInfoImageView;
    NotificationReceiver notificationReceiver = new NotificationReceiver();
    private boolean isPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPlaying = false;
        playImageView = findViewById(R.id.play_pause_button_image);
        playImageView.setOnClickListener(this);

        mInfoImageView = findViewById(R.id.information_button_image);
        mInfoImageView.setOnClickListener(this);

        // Registering the receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION.ACTION_EXAMPLE);
        registerReceiver(notificationReceiver, filter);
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
        unregisterReceiver(notificationReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_pause_button_image:
                if (isPlaying) {
                    Intent pauseIntent = new Intent(MainActivity.this, ForegroundService.class);
                    pauseIntent.setAction(Constants.ACTION.ACTION_PAUSE);
                    ContextCompat.startForegroundService(this, pauseIntent);
                    playImageView.setImageResource(R.drawable.play_button_image);
                    isPlaying = false;
                } else {
                    Intent playIntent = new Intent(MainActivity.this, ForegroundService.class);
                    playIntent.setAction(Constants.ACTION.ACTION_PLAY);
                    ContextCompat.startForegroundService(this, playIntent);
                    playImageView.setImageResource(R.drawable.pause_button_image);
                    isPlaying = true;
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

    
}
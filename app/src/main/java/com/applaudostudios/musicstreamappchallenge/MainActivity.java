package com.applaudostudios.musicstreamappchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView startButton = findViewById(R.id.play_button_image);
        ImageView stopButton = findViewById(R.id.pause_button_image);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button_image:
                Intent startIntent = new Intent(MainActivity.this, ForegroundService.class);
                startIntent.setAction(Constants.ACTION.START_FOREGROUND_ACTION);
                startService(startIntent);
                break;
            case R.id.pause_button_image:
                Intent stopIntent = new Intent(MainActivity.this, ForegroundService.class);
                stopIntent.setAction(Constants.ACTION.STOP_FOREGROUND_ACTION);
                startService(stopIntent);
                break;

            default:
                break;
        }
    }

}
package com.applaudostudios.musicstreamappchallenge;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String url = null;
    private ImageView playImageView;
    private ImageView pauseImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.url_text_view);
        url = textView.getText().toString();

        playImageView = findViewById(R.id.play_button_image);
        pauseImageView = findViewById(R.id.pause_button_image);

        playImageView.setOnClickListener(this);
        pauseImageView.setOnClickListener(this);

    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", url);

        // To start your service while the app itself it is in the background
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_button_image:
                startService();
                break;
            case R.id.pause_button_image:
                stopService();
                break;
            default:
                break;
        }
    }


}
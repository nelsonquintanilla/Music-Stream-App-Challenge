package com.applaudostudios.musicstreamappchallenge;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.url_text_view);
        url = textView.getText().toString();

        playImageView = findViewById(R.id.play_button_image);
        isPlaying = false;

        playImageView.setOnClickListener(this);
    }

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

    public void killService() {
        Intent killIntent = new Intent(this, ForegroundService.class);
        stopService(killIntent);
    }

    // Stops the service only if the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        killService();
    }


}
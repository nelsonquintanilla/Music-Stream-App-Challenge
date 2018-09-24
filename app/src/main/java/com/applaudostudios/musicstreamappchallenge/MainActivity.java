package com.applaudostudios.musicstreamappchallenge;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Parcelable {
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

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        // Always call the superclass so it can restore the view hierarchy
//        super.onRestoreInstanceState(savedInstanceState);
//        // Restore state members from saved instance
//        isPlaying = savedInstanceState.getBoolean("BOOLEAN_KEY");
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("BOOLEAN_KEY", isPlaying);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    // Parcelable generated methods.
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isPlaying ? (byte) 1 : (byte) 0);
    }

    public MainActivity() {
    }

    protected MainActivity(Parcel in) {
        this.isPlaying = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MainActivity> CREATOR = new Parcelable.Creator<MainActivity>() {
        @Override
        public MainActivity createFromParcel(Parcel source) {
            return new MainActivity(source);
        }

        @Override
        public MainActivity[] newArray(int size) {
            return new MainActivity[size];
        }
    };



}
package com.applaudostudios.musicstreamappchallenge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    // This method can execute it's code without the app haven't to be open
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastmessage");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



}

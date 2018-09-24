package com.applaudostudios.musicstreamappchallenge;

public class Constants {
    public interface ACTION {
        public static final String ACTION_PLAY = "com.applaudostudios.musicstreamappchallenge.ACTION_PLAY";
        public static final String ACTION_PAUSE = "com.applaudostudios.musicstreamappchallenge.ACTION_PAUSE";
        public static final String ACTION_EXAMPLE = "com.applaudostudios.musicstreamappchallenge.ACTION_EXAMPLE";
    }

    public interface NOTIFICATION_ID {
        public static final int FOREGROUND_SERVICE = 1;
    }

    public interface CHANNEL_ID {
        public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    }

}

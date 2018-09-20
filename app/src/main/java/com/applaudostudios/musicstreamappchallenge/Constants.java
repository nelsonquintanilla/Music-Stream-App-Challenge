package com.applaudostudios.musicstreamappchallenge;

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.applaudostudios.musicstreamappchallenge.action.main";
        public static String PREV_ACTION = "com.applaudostudios.musicstreamappchallenge.action.prev";
        public static String PLAY_ACTION = "com.applaudostudios.musicstreamappchallenge.action.play";
        public static String NEXT_ACTION = "com.applaudostudios.musicstreamappchallenge.action.next";
        public static String START_FOREGROUND_ACTION = "com.applaudostudios.musicstreamappchallenge.action.start_foreground";
        public static String STOP_FOREGROUND_ACTION = "com.applaudostudios.musicstreamappchallenge.action.stop_foreground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}

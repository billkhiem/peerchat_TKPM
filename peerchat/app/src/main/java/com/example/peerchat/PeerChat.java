package com.example.peerchat;

import android.app.Application;
import android.content.Context;

public class PeerChat extends Application {

        private static Context context;

        public void onCreate() {
            super.onCreate();
            PeerChat.context = getAppContext();
        }

        public static Context getAppContext() {
            return PeerChat.context;
        }

}

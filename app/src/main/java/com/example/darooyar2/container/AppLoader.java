package com.example.darooyar2.container;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class AppLoader extends Application {

    private static Context context;
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler  = new Handler(Looper.getMainLooper());
    }

    public static Context getAppContext(){
        return context;
    }
}

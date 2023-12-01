package com.health.darooyar.container;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

public class AppLoader extends Application {

    private static Context context;
    public static Handler handler;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler  = new Handler(Looper.getMainLooper());

        sharedPreferences = getSharedPreferences("DaroYar", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static Context getAppContext(){
        return context;
    }
}

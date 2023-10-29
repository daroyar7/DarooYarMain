package com.example.darooyar2.container;

import android.app.Application;
import android.content.Context;

public class AppLoader extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext(){
        return context;
    }
}

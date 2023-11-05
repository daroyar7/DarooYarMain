package com.example.darooyar2.them;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.StateSet;
import android.view.View;
import android.widget.TextView;

import com.example.darooyar2.container.AppLoader;

public class AppTheme {

    private int diameter;
    private volatile static AppTheme appTheme;
    public float widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
    public float heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Typeface regularTypeface;
    private Typeface mediumTypeface;

    public static AppTheme getInstance() {
        AppTheme localInstance = appTheme;
        if (localInstance == null) {
            synchronized (AppTheme.class) {
                localInstance = appTheme;
                if (localInstance == null)
                    appTheme = localInstance = new AppTheme();
            }
        }
        return localInstance;
    }

    private AppTheme() {
        diameter = (int) (Math.sqrt(Math.pow(widthPixels, 2) + Math.pow(heightPixels, 2)) / 2.3f);

        regularTypeface = Typeface.createFromAsset(AppLoader.getAppContext().getAssets(), "regular.ttf");
        mediumTypeface = Typeface.createFromAsset(AppLoader.getAppContext().getAssets(), "medium.ttf");
    }

    public Typeface getRegularTypeface(){
        return regularTypeface;
    }

    public Typeface getMediumTypeface(){
        return mediumTypeface;
    }

    public int getAf(int dimen) {
        return (int) (dimen * 0.001f * diameter);
    }

    public void setUpStatusBar(Activity activity, int statusBarColor, boolean isStatusIconLight) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(isStatusIconLight ? decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        activity.getWindow().setStatusBarColor(statusBarColor);
    }
}
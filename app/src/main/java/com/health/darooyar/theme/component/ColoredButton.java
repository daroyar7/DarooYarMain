package com.health.darooyar.theme.component;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Param;


public class ColoredButton extends FrameLayout {

    private TextView txtText;
    private int hoverColor;
    private int backgroundColor;

    public ColoredButton(Context context) {
        super(context);
    }

    public void setup(String text, int textColor, int hoverColor, int backgroundColor, int fontSize, OnClickListener onClickListener) {
        removeAllViews();
        this.hoverColor = hoverColor;
        this.backgroundColor = backgroundColor;
        setOnClickListener(onClickListener);

        AppTheme appTheme = AppTheme.getInstance();

        Context context = getContext();
        if (context == null)
            return;

        txtText = new TextView(context);
        txtText.setTextSize(0, fontSize);
        txtText.setTextColor(textColor);
        txtText.setTypeface(appTheme.getMediumTypeface());
        txtText.setText(text);
        txtText.setGravity(Gravity.CENTER);
        setBackground(AppTheme.createRoundSelectorDrawable(hoverColor, backgroundColor, appTheme.getAf(20),  appTheme.getAf(20)));
        addView(txtText, Param.frameParam(-2, -2, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, -1, -1, -1, -1));

    }

    public void changePadding(int left, int top, int right, int bottom) {
        txtText.setPadding(left, top, right, bottom);
    }

    public void changeRadius(int radius) {
        setBackground(AppTheme.createRoundSelectorDrawable(hoverColor, backgroundColor, radius, radius));
    }

    public void changeBtnListener(OnClickListener onClickListener) {
        setOnClickListener(onClickListener);
    }

    public void changeText(String text) {
        txtText.setText(text);
    }
}

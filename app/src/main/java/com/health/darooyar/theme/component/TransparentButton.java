package com.health.darooyar.theme.component;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;

public class TransparentButton extends FrameLayout {

    private TextView txtText;
    private boolean isIconRight;

    public TransparentButton(@NonNull Context context) {
        super(context);
    }

    public void setup(String text, int textColor, int fontSize, Typeface typeface, String language, int icon, boolean isIconRight, OnClickListener onClickListener) {
        removeAllViews();
        setOnClickListener(onClickListener);
        this.isIconRight = isIconRight;

        Context context = getContext();
        if (context == null)
            return;

        AppTheme appTheme = AppTheme.getInstance();

        txtText = new TextView(getContext());
        txtText.setText(Html.fromHtml(text));
        txtText.setGravity(Gravity.CENTER);
        txtText.setTextSize(0, fontSize);
        if (language.equals("en"))
            txtText.setTypeface(txtText.getTypeface(), Typeface.BOLD);
        else
            txtText.setTypeface(typeface);
        if (icon != 0) {
            if (isIconRight) {
                txtText.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
            } else {
                txtText.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            }
            txtText.setCompoundDrawablePadding(appTheme.getAf(20));
        }
        txtText.setTextColor(textColor);
        txtText.setPadding(appTheme.getAf(25), appTheme.getAf(20), appTheme.getAf(25), appTheme.getAf(20));
        setBackground(AppTheme.createRoundSelectorDrawable(Color.btnTransparentHover(), Color.transparent(), appTheme.getAf(20), appTheme.getAf(20)));
        addView(txtText, Param.frameParam(-2, -2, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, -1, -1, -1, -1));

    }
    public void setIconDrawable(Drawable icon , boolean isIconRight){
        if (isIconRight) {
            txtText.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        } else {
            txtText.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        }
        AppTheme appTheme = AppTheme.getInstance();
        txtText.setCompoundDrawablePadding(appTheme.getAf(20));
    }

    public void changeGravity(int gravity) {
        if (gravity == Gravity.RIGHT) {
            txtText.setLayoutParams(Param.frameParam(-2, -2, Gravity.RIGHT | Gravity.CENTER_VERTICAL, -1, -1, -1, -1));
        }
    }

    public void changePadding(int left, int top, int right, int bottom) {
        txtText.setPadding(0, 0, 0, 0);
        txtText.setPadding(left, top, right, bottom);
    }

    public void changeIconPadding(int padding) {
        txtText.setCompoundDrawablePadding(padding);
    }

    public void changeText(String text) {
        txtText.setText(Html.fromHtml(text));
    }

    public void changeTextColor(int color) {
        txtText.setTextColor(color);
    }

    public void changeIcon(int icon) {
        if (isIconRight) {
            txtText.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
        } else {
            txtText.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        }
        if (icon != 0)
            txtText.setCompoundDrawablePadding(20);
        else
            txtText.setCompoundDrawablePadding(0);
    }

    public String getText() {
        return txtText.getText().toString();
    }
}

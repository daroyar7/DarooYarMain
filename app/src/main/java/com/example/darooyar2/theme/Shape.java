package com.example.darooyar2.theme;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.StateSet;
import android.widget.TextView;

public class Shape {

    private static AppTheme appTheme = AppTheme.getInstance();

    public static Drawable createRoundDrawable(int radius, int backgroundColor) {
        ShapeDrawable roundDrawable = new ShapeDrawable();
        roundDrawable.setShape(new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null));
        roundDrawable.getPaint().setColor(backgroundColor);
        return roundDrawable;
    }

    public static Drawable createRoundDrawable(int tLR, int tRR, int bLR, int bRR, int backgroundColor) {
        ShapeDrawable defaultDrawable = new ShapeDrawable();
        defaultDrawable.setShape(new RoundRectShape(new float[]{tLR, tLR, tRR, tRR, bRR, bRR, bLR, bLR}, null, null));
        defaultDrawable.getPaint().setColor(backgroundColor);
        return defaultDrawable;
    }

    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables())
            if (drawable != null)
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public static Drawable createBorderRoundDrawable(int borderColor, int backgroundColor, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(appTheme.getAf(3), borderColor);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static Drawable createBorderRoundDrawable(int borderColor, int backgroundColor, int tR, int bR) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(appTheme.getAf(3), borderColor);
        gradientDrawable.setCornerRadii(new float[]{tR, tR, tR, tR, bR, bR, bR, bR});
        return gradientDrawable;
    }

    public static Drawable createBorderRoundDrawable(int borderColor, int backgroundColor, int radius, int dashWidth, int dashGap) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(appTheme.getAf(3), borderColor, dashWidth, dashGap);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static Drawable createGradient(int colorGradientOne, int colorGradientTwo, int radius) {
        return createGradient(colorGradientOne, colorGradientTwo, radius, GradientDrawable.Orientation.LEFT_RIGHT);
    }

    public static Drawable createGradient(int colorGradientOne, int colorGradientTwo, int radius, GradientDrawable.Orientation orientation) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, new int[]{colorGradientOne
                , colorGradientTwo});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static StateListDrawable createRoundFocusableDrawable(int backgroundColor, int enableBorder, int disableBorder, int radius) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{-android.R.attr.state_focused}, createBorderRoundDrawable(disableBorder, backgroundColor, radius));
        out.addState(new int[]{android.R.attr.state_focused}, createBorderRoundDrawable(enableBorder, backgroundColor, radius));
        return out;
    }

    public static StateListDrawable createRoundFocusableDrawable(int enableBackgroundColor, int disabledBackgroundColor, int enableBorder, int disableBorder, int radius) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{-android.R.attr.state_focused}, createBorderRoundDrawable(disableBorder, disabledBackgroundColor, radius));
        out.addState(new int[]{android.R.attr.state_focused}, createBorderRoundDrawable(enableBorder, enableBackgroundColor, radius));
        return out;
    }

    public static StateListDrawable createRoundOnClickedStateDrawable(int enableBackgroundColor, int disabledBackgroundColor, int enableBorder, int disableBorder, int radius) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{-android.R.attr.state_pressed}, createBorderRoundDrawable(disableBorder, disabledBackgroundColor, radius));
        out.addState(new int[]{android.R.attr.state_pressed}, createBorderRoundDrawable(enableBorder, enableBackgroundColor, radius));
        return out;
    }

    public static Drawable createRoundSelectorDrawable(int rippleColor, int backgroundColor, int radius) {
        Drawable contentDrawable = createRoundDrawable(radius, radius, radius, radius, backgroundColor);

        Drawable maskDrawable = createRoundDrawable(radius, radius, radius, radius, 0xffffffff);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{StateSet.WILD_CARD},
                new int[]{rippleColor}
        );
        return new RippleDrawable(colorStateList, contentDrawable, maskDrawable);
    }

    public static Drawable createRoundSelectorDrawable(int rippleColor, int backgroundColor, int tLR, int tRR, int bLR, int bRR) {
        Drawable contentDrawable = createRoundDrawable(tLR, tRR, bLR, bRR, backgroundColor);

        Drawable maskDrawable = createRoundDrawable(tLR, tRR, bLR, bRR, 0xffffffff);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{StateSet.WILD_CARD},
                new int[]{rippleColor}
        );
        return new RippleDrawable(colorStateList, contentDrawable, maskDrawable);
    }
}

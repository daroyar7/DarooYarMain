package com.example.darooyar2.theme.component.NumberPicker;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.numberpicker.NumberPicker;

public class DurationPicker extends LinearLayout {
    private NumberPicker numberPicker;
    private AppTheme appTheme=AppTheme.getInstance();
    private OnDurationChangedListener listener;

    public void setListener(OnDurationChangedListener listener) {
        this.listener = listener;
    }

    public DurationPicker(Context context) {
        super(context);

        int minNumber = 1;
        int maxNumber = 30;

        numberPicker=new NumberPicker(context);
        numberPicker.setTypeface(appTheme.getMediumTypeface());
        numberPicker.setSelectedTypeface(appTheme.getRegularTypeface());
        numberPicker.setTextColor(Color.parseColor("#818181"));
        numberPicker.setSelectedTextColor(Color.parseColor("#202020"));
        numberPicker.setFormatter("%d");
        numberPicker.setDividerColor(0);
        numberPicker.setFadingEdgeEnabled(false);

        NumberPicker.OnValueChangeListener dateChangeListener = (picker, oldVal, newVal) -> {
            if (listener != null)
                listener.onDurationChanged(numberPicker.getValue());
        };
        numberPicker.setMinValue(minNumber);
        numberPicker.setMaxValue(maxNumber);
        numberPicker.setValue(minNumber);

        numberPicker.setOnValueChangedListener(dateChangeListener);

    }public interface OnDurationChangedListener{
        void onDurationChanged(int duration);
    }
}

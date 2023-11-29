package com.example.darooyar2.theme.component.NumberPicker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.darooyar2.R;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.numberpicker.NumberPicker;

public class DurationPicker extends LinearLayout {
    private NumberPicker numberPicker;
    private AppTheme appTheme = AppTheme.getInstance();
    private OnDurationChangedListener listener;

    public void setListener(OnDurationChangedListener listener) {
        this.listener = listener;
    }

    public DurationPicker(Context context, int defaultValue) {
        super(context);

        int minNumber = 1;
        int maxNumber = 30;

        View v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.duration_picker, this);
        numberPicker = v.findViewById(R.id.numberPicker_duration);
        numberPicker.setTypeface(appTheme.getMediumTypeface());
        numberPicker.setSelectedTypeface(appTheme.getRegularTypeface());
        numberPicker.setTextColor(Color.parseColor("#818181"));
        numberPicker.setSelectedTextColor(Color.parseColor("#202020"));
        numberPicker.setFormatter("%d");
        numberPicker.setDividerColor(0);
        numberPicker.setFadingEdgeEnabled(true);

        NumberPicker.OnValueChangeListener dateChangeListener = (picker, oldVal, newVal) -> {
            if (listener != null)
                listener.onDurationChanged(numberPicker.getValue());
        };
        numberPicker.setMinValue(minNumber);
        numberPicker.setMaxValue(maxNumber);
        numberPicker.setValue(defaultValue == 0 ? minNumber : defaultValue);

        numberPicker.setOnValueChangedListener(dateChangeListener);

    }

    public void disable(){
        numberPicker.setEnabled(false);
    }

    public interface OnDurationChangedListener {
        void onDurationChanged(int duration);
    }
}

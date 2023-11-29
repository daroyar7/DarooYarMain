package com.example.darooyar2.theme.component;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.component.PersionDateTime.date.DatePickerDialog;
import com.example.darooyar2.theme.component.PersionDateTime.time.RadialPickerLayout;
import com.example.darooyar2.theme.component.PersionDateTime.time.TimePickerDialog;
import com.example.darooyar2.theme.component.PersionDateTime.utils.PersianCalendar;
import com.example.darooyar2.utils.PersianDate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;

public class TimePickerView extends ConstraintLayout {
    private ContainerActivity activity;
    private AppTheme appTheme = AppTheme.getInstance();

    public static final int idIcon=8485;
    public static final int idTextField=8488;
    private TextInputEditText editText;
    private TextInputLayout textInputLayout;


    public TimePickerView(@NonNull Context context) {
        super(context);
        activity = (ContainerActivity) context;
    }
    public void setUp(int icon , String hint ,String text){
        ImageView icField = new ImageView(activity);
        icField.setId(idIcon);
        icField.setImageResource(icon);
        icField.setColorFilter(Color.getOnBackgroundColor());
        addView(icField, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), 0, -1, 0, 0));

        textInputLayout = new TextInputLayout(activity);
        textInputLayout.setHint(hint);
        textInputLayout.setId(idTextField);
        addView(textInputLayout, Param.consParam(0, -2, icField.getId(), 0, -icField.getId(), icField.getId(), -1, -1, Dimen.m16, -1));

        editText=new TextInputEditText(activity);
        editText.setGravity(Gravity.CENTER);
        editText.setText(text);
        editText.setOnClickListener(v->{
            PersianDate.install();
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance((view, hourOfDay, minute) -> {
                        String date = hourOfDay+" : "+minute;
                        (editText).setText(MessageFormat.format("{0}", date));

            },
                    PersianDate.getHour(),
                    PersianDate.getMinute(),
                    true
            );
            timePickerDialog.show(activity.getFragmentManager(), null);
        });
        textInputLayout.addView(editText , Param.linearParam(-1 , -2 , Gravity.CENTER, -1 ,-1 ,-1 ,-1));
    }
    public void setEnabled(boolean isEnabled){
        editText.setEnabled(false);
    }

    public String getText(){
        return editText.getText().toString();
    }
    public void setError(String err){
        textInputLayout.setError(err);
    }
}

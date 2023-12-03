package com.health.darooyar.theme.component;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.health.darooyar.container.ContainerActivity;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.component.PersionDateTime.time.TimePickerDialog;
import com.health.darooyar.utils.PersianDate;
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
        addView(icField, Param.consParam(appTheme.getAf(170), appTheme.getAf(170), 0, -1, 0, 0));

        textInputLayout = new TextInputLayout(activity);
        textInputLayout.setHint(hint);
        textInputLayout.setTypeface(appTheme.getRegularTypeface());
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textInputLayout.setBoxCornerRadii(appTheme.getAf(25), appTheme.getAf(25), appTheme.getAf(25), appTheme.getAf(25));
        textInputLayout.setPadding(appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(35), appTheme.getAf(5));
        textInputLayout.setId(idTextField);
        addView(textInputLayout, Param.consParam(0, -2, icField.getId(), 0, -icField.getId(), icField.getId(), -1, -1, -1, -1));

        editText=new TextInputEditText(activity);
        editText.setPadding(appTheme.getAf(0), appTheme.getAf(35), appTheme.getAf(20), appTheme.getAf(30));
        editText.setTypeface(appTheme.getRegularTypeface());
        editText.setBackground(null);
        editText.setTextSize(0, appTheme.getAf(40));
        editText.setGravity(Gravity.CENTER);
        editText.setText(text);
        editText.setOnClickListener(v->{
            PersianDate.install();
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance((view, hourOfDay, minute) -> {
                        String date = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
                        (editText).setText(MessageFormat.format("{0}", date));

            },
                    PersianDate.getHour(),
                    PersianDate.getMinute(),
                    true
            );
            timePickerDialog.show(activity.getFragmentManager(), null);
        });
        textInputLayout.addView(editText, Param.linearParam(-1, -2, Gravity.CENTER, appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(5)));
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

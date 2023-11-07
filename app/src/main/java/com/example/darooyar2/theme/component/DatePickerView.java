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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePickerView extends ConstraintLayout {
    private ContainerActivity activity;
    private AppTheme appTheme = AppTheme.getInstance();

    public static final int idIcon=8485;
    public static final int idTextField=8488;
    private TextInputEditText editText;
    private TextInputLayout textInputLayout;


    public DatePickerView(@NonNull Context context) {
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
        editText.setText(text);
        editText.setOnClickListener(v->{
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("انتخاب تاریخ")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                String date = new SimpleDateFormat("yyy/MM/dd", Locale.getDefault()).format(new Date(selection));
                (editText).setText(MessageFormat.format("{0}", date));
            });
            materialDatePicker.show(activity.getSupportFragmentManager(), "tag");
        });
        textInputLayout.addView(editText , Param.linearParam(-1 , -2 , Gravity.CENTER, -1 ,-1 ,-1 ,-1));
    }

    public String getText(){
        return editText.getText().toString();
    }
    public void setError(String err){
        textInputLayout.setError(err);
    }
}

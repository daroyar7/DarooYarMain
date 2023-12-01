package com.example.darooyar2.theme.component;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.component.PersionDateTime.date.DatePickerDialog;
import com.example.darooyar2.theme.component.PersionDateTime.utils.PersianCalendar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;

public class DatePickerView extends ConstraintLayout {
    private ContainerActivity activity;
    private AppTheme appTheme = AppTheme.getInstance();

    public static final int idIcon = 8485;
    public static final int idTextField = 8488;
    private TextInputEditText editText;
    private TextInputLayout textInputLayout;
    private DatePickerDialog datePickerDialog;


    public DatePickerView(@NonNull Context context) {
        super(context);
        activity = (ContainerActivity) context;
    }

    public void setUp(int icon, String hint, String text) {
        ImageView icField = new ImageView(activity);
        icField.setId(idIcon);
        icField.setImageResource(icon);
        icField.setColorFilter(Color.getOnBackgroundColor());
        addView(icField, Param.consParam(appTheme.getAf(170), appTheme.getAf(170), 0, -1, 0, 0));

        textInputLayout = new TextInputLayout(activity);
        textInputLayout.setHint(hint);

        textInputLayout.setId(idTextField);
        textInputLayout.setTypeface(appTheme.getRegularTypeface());
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textInputLayout.setBoxCornerRadii(appTheme.getAf(25), appTheme.getAf(25), appTheme.getAf(25), appTheme.getAf(25));
        textInputLayout.setPadding(appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(35), appTheme.getAf(5));
        textInputLayout.setOnClickListener(onClickListener);
        addView(textInputLayout, Param.consParam(0, -2, icField.getId(), 0, -icField.getId(), icField.getId(), -1, -1, 0, -1));

        editText = new TextInputEditText(activity);
        editText.setText(text);
        editText.setPadding(appTheme.getAf(0), appTheme.getAf(35), appTheme.getAf(20), appTheme.getAf(30));
        editText.setTypeface(appTheme.getRegularTypeface());
        editText.setBackground(null);
        editText.setTextSize(0, appTheme.getAf(40));
        editText.setGravity(Gravity.CENTER);
        editText.setOnClickListener(onClickListener);
        textInputLayout.addView(editText, Param.linearParam(-1, -2, Gravity.CENTER, appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(5)));
    }

    private OnClickListener onClickListener = v -> {
        PersianCalendar persianCalendar = new PersianCalendar();
        if (datePickerDialog == null) { //dashtam say mikardam bugesho raf konam ke nashod =(
            datePickerDialog = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
                        String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                        (editText).setText(MessageFormat.format("{0}", date));
                        datePickerDialog = null;
                    },
                    persianCalendar.getPersianYear(),
                    persianCalendar.getPersianMonth(),
                    persianCalendar.getPersianDay()
            );
            datePickerDialog.show(activity.getFragmentManager(), null);
        }
    };

    public String getText() {
        return editText.getText().toString();
    }

    public void setError(String err) {
        textInputLayout.setError(err);
    }

    public void setEnabled(boolean isEnabled) {
        editText.setEnabled(false);
    }
}

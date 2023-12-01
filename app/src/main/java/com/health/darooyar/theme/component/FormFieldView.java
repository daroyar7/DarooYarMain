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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormFieldView extends ConstraintLayout {
    private ContainerActivity activity;
    private AppTheme appTheme = AppTheme.getInstance();
    public static final int idIcon = 8485;
    public static final int idTextField = 8488;
    private TextInputEditText editText;
    private TextInputLayout textInputLayout;

    public FormFieldView(@NonNull Context context) {
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
        textInputLayout.setBoxCornerRadii(appTheme.getAf(25), appTheme.getAf(25), appTheme.getAf(25), appTheme.getAf(25));
        textInputLayout.setTypeface(appTheme.getRegularTypeface());
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textInputLayout.setPadding(appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(35), appTheme.getAf(5));
        textInputLayout.setId(idTextField);
        addView(textInputLayout, Param.consParam(0, -2, icField.getId(), 0, -icField.getId(), icField.getId(), -1, -1,0, -1));

        editText = new TextInputEditText(activity);
        editText.setText(text);
        editText.setPadding(appTheme.getAf(0), appTheme.getAf(35), appTheme.getAf(20), appTheme.getAf(30));
        editText.setBackground(null);
        editText.setTextSize(0, appTheme.getAf(40));
        editText.setTypeface(appTheme.getRegularTypeface());
        editText.setGravity(Gravity.CENTER);
        textInputLayout.addView(editText, Param.linearParam(-1, -2, Gravity.CENTER, appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(5), appTheme.getAf(5)));

    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setError(String err) {
        textInputLayout.setError(err.trim());
    }

    public void setEnabled(boolean isEnabled) {
        editText.setEnabled(false);
    }

}

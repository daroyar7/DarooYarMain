package com.example.darooyar2.them.component;

import android.content.Context;
import android.view.Gravity;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.them.AppTheme;
import com.example.darooyar2.them.Color;
import com.example.darooyar2.them.Dimen;
import com.example.darooyar2.them.Param;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormFieldView extends ConstraintLayout {
    private ContainerActivity activity;
    private AppTheme appTheme = AppTheme.getInstance();
    public static final int idIcon=8485;
    public static final int idTextField=8488;
    private TextInputEditText editText;
    private TextInputLayout textInputLayout;

    public FormFieldView(@NonNull Context context) {
        super(context);
        activity = (ContainerActivity) context;
    }

    public void setUp( int icon, String hint) {
        ImageView icField = new ImageView(activity);
        icField.setId(idIcon);
        icField.setImageResource(icon);
        icField.setColorFilter(Color.getOnBackgroundColor());
        addView(icField, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), 0, -1, 0, 0));

        textInputLayout = new TextInputLayout(activity);
        textInputLayout.setHint(hint);
        textInputLayout.setPadding(0,0,appTheme.getAf(30),0);
        textInputLayout.setId(idTextField);
        addView(textInputLayout, Param.consParam(0, -2, icField.getId(), 0, -icField.getId(), icField.getId(), -1, -1, Dimen.m16, -1));

        editText=new TextInputEditText(activity);
        textInputLayout.addView(editText , Param.linearParam(-1 , -2 , Gravity.CENTER, -1 ,-1 ,-1 ,-1));
    }
    public String getText(){
        return editText.getText().toString();
    }
    public void setError(String err){
        textInputLayout.setError(err.trim());
    }

}

package com.example.darooyar2.feature.alert.presentation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.R;
import com.example.darooyar2.container.AppLoader;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;

public class MedicineHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private TextView tvDate;
    private CheckBox checkBox;
    private ConstraintLayout parent;

    protected MedicineHolder(ConstraintLayout parent) {
        super(parent);
        Context context = parent.getContext();
        AppTheme appTheme = AppTheme.getInstance();
        this.parent = parent;
        parent.setPadding(-1,-1,-1,Dimen.m16);

        final int TXT_DOCTOR_ID = 484;
        tvName = new TextView(context);
        tvName.setId(TXT_DOCTOR_ID);
        tvName.setTypeface(appTheme.getMediumTypeface(), Typeface.BOLD);
        tvName.setTextSize(0, appTheme.getAf(Dimen.fontSize20));
        tvName.setTypeface(appTheme.getRegularTypeface());
        tvName.setTextColor(Color.getOnBackgroundColor());
        parent.addView(tvName, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        tvDate = new TextView(context);
        tvDate.setTextSize(0, appTheme.getAf(Dimen.fontSize16));
        tvDate.setTypeface(appTheme.getRegularTypeface());
        tvDate.setTextColor(Color.getOnBackgroundColor());
        parent.addView(tvDate, Param.consParam(-2, -2, -TXT_DOCTOR_ID, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        checkBox = new CheckBox(parent.getContext());
        parent.addView(checkBox, Param.consParam(-2, -2, 0, 0, -1, 0, -1, Dimen.m16));
    }

    public void setTvName(String name) {
        tvName.setText(name);
    }


    public void setTvDate(String duration) {
        tvDate.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        tvDate.setText(duration);
    }

    public CheckBox getCheckBox(){
        return checkBox;
    }

    public void takingMedicine(long timeMustUsed, int medicineModelHash){
        AppLoader.editor.putBoolean(timeMustUsed + medicineModelHash + "", true).apply();
        Log.i("TAG", "takingMedicine: "+ timeMustUsed);
    }
}

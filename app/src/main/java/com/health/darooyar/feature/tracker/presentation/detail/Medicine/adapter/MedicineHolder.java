package com.health.darooyar.feature.tracker.presentation.detail.Medicine.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.health.darooyar.R;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;

public class MedicineHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private TextView tvDuration;
    private TextView tvDate;
    private ImageView imgEdit;
    private ImageView imgDelete;
    private ConstraintLayout parent;

    protected MedicineHolder(ConstraintLayout parent) {
        super(parent);
        Context context = parent.getContext();
        AppTheme appTheme = AppTheme.getInstance();
        this.parent = parent;
        parent.setElevation(appTheme.getAf(10));
        parent.setPadding(-1,-1,-1,Dimen.m16);

        final int TXT_DOCTOR_ID = 484;
        tvName = new TextView(context);
        tvName.setId(TXT_DOCTOR_ID);
        tvName.setTypeface(appTheme.getMediumTypeface(), Typeface.BOLD);
        tvName.setTextSize(0, appTheme.getAf(Dimen.fontSize20));
        tvName.setTypeface(appTheme.getRegularTypeface());
        tvName.setTextColor(Color.getOnBackgroundColor());
        parent.addView(tvName, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        final int TXT_DURATION_ID = 852;
        tvDuration = new TextView(context);
        tvDuration.setId(TXT_DURATION_ID);
        tvDuration.setTextSize(0, appTheme.getAf(Dimen.fontSize16));
        tvDuration.setTypeface(appTheme.getRegularTypeface());
        tvDuration.setTextColor(Color.getOnBackgroundColor());
        parent.addView(tvDuration, Param.consParam(-2, -2, -TXT_DOCTOR_ID, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        tvDate = new TextView(context);
        tvDate.setTextSize(0, appTheme.getAf(Dimen.fontSize16));
        tvDate.setTypeface(appTheme.getRegularTypeface());
        tvDate.setTextColor(Color.getOnBackgroundColor());
        parent.addView(tvDate, Param.consParam(-2, -2, -TXT_DURATION_ID, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        final int IMG_EDIT_ID = 8485;
        imgEdit = new ImageView(context);
        imgEdit.setId(IMG_EDIT_ID);
        imgEdit.setImageResource(R.drawable.ic_edit);
        imgEdit.setBackground(AppTheme.createRoundSelectorDrawable(Color.black10Op(),0,appTheme.getAf(100),appTheme.getAf(100)));
        imgEdit.setPadding(appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20));
        parent.addView(imgEdit, Param.consParam(appTheme.getAf(90), appTheme.getAf(90), 0, 0, -1, 0, Dimen.m16, Dimen.m16, -1, -1));

        imgDelete = new ImageView(context);
        imgDelete.setImageResource(R.drawable.ic_remove);
        imgDelete.setBackground(AppTheme.createRoundSelectorDrawable(Color.black10Op(),0,appTheme.getAf(100),appTheme.getAf(100)));
        imgDelete.setPadding(appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20));
        parent.addView(imgDelete, Param.consParam(appTheme.getAf(90), appTheme.getAf(90), 0, -IMG_EDIT_ID, -1,0, Dimen.m16, Dimen.m16, -1, -1));
    }

    public void setTvName(String name) {
        tvName.setText("نام دارو: " + name);
    }

    public void setTvDuration(String duration) {
        tvDuration.setText("دوره: " + duration);
    }
    public void setTvDate(String duration) {
        tvDate.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        tvDate.setText(duration);
    }

    public void editClicked(View.OnClickListener onClickListener){
        imgEdit.setOnClickListener(onClickListener);
    }

    public void deleteClicked(View.OnClickListener onClickListener){
        imgDelete.setOnClickListener(onClickListener);
    }

}

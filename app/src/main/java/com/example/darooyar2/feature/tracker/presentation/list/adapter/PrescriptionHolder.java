package com.example.darooyar2.feature.tracker.presentation.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.R;
import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.feature.tracker.presentation.detail.PrescriptionDetailFragment;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;

public class PrescriptionHolder extends RecyclerView.ViewHolder {

    private TextView txtDoctorName;
    private TextView txtDate;
    private ImageView imgEdit;
    private ImageView imgDelete;
    private ConstraintLayout parent;

    protected PrescriptionHolder(ConstraintLayout parent) {
        super(parent);
        Context context = parent.getContext();
        AppTheme appTheme = AppTheme.getInstance();
        this.parent = parent;
        parent.setPadding(-1,-1,-1,Dimen.m16);
        parent.setElevation(appTheme.getAf(10));

        final int TXT_DOCTOR_ID = 484;
        txtDoctorName = new TextView(context);
        txtDoctorName.setId(TXT_DOCTOR_ID);
        txtDoctorName.setTextSize(0, appTheme.getAf(Dimen.fontSize20));
        txtDoctorName.setTypeface(appTheme.getRegularTypeface());
        txtDoctorName.setTextColor(Color.getOnBackgroundColor());
        parent.addView(txtDoctorName, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        txtDate = new TextView(context);
        txtDate.setTextSize(0, appTheme.getAf(Dimen.fontSize20));
        txtDate.setTypeface(appTheme.getRegularTypeface());
        txtDate.setTextColor(Color.getOnBackgroundColor());
        parent.addView(txtDate, Param.consParam(-2, -2, -TXT_DOCTOR_ID, -1, 0, -1, Dimen.m16, -1, Dimen.m24, -1));

        final int IMG_EDIT_ID = 8485;
        imgEdit = new ImageView(context);
        imgEdit.setId(IMG_EDIT_ID);
        imgEdit.setImageResource(R.drawable.ic_edit);
        imgEdit.setBackground(AppTheme.createRoundSelectorDrawable(Color.black10Op(),0,appTheme.getAf(100),appTheme.getAf(100)));
        imgEdit.setPadding(appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20));
        parent.addView(imgEdit, Param.consParam(appTheme.getAf(90), appTheme.getAf(90), 0, 0, -1, 0, Dimen.m16, Dimen.m24, -1, -1));

        imgDelete = new ImageView(context);
        imgDelete.setImageResource(R.drawable.ic_remove);
        imgDelete.setBackground(AppTheme.createRoundSelectorDrawable(Color.black10Op(),0,appTheme.getAf(100),appTheme.getAf(100)));
        imgDelete.setPadding(appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20),appTheme.getAf(20));
        parent.addView(imgDelete, Param.consParam(appTheme.getAf(90), appTheme.getAf(90), 0, -IMG_EDIT_ID, -1, 0, Dimen.m16, Dimen.m24, -1, -1));
    }

    public void setDoctorName(String doctorName) {
        if (doctorName.contains("دکتر"))
            txtDoctorName.setText(doctorName);
        else
            txtDoctorName.setText("دکتر: " + doctorName);
    }

    public void setDate(String date) {
        txtDate.setText("تاریخ مراجعه: " + date);
    }

    public void editClicked(View.OnClickListener onClickListener){
        imgEdit.setOnClickListener(onClickListener);
    }

    public void deleteClicked(View.OnClickListener onClickListener){
        imgDelete.setOnClickListener(onClickListener);
    }

    public void parentClicked(View.OnClickListener onClickListener){
        parent.setOnClickListener(onClickListener);
    }
}

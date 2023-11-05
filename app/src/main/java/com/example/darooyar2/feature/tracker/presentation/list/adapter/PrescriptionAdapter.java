package com.example.darooyar2.feature.tracker.presentation.list.adapter;

import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.Shape;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionHolder> {

    private PrescriptionModel[] prescriptionModels;

    public PrescriptionAdapter(PrescriptionModel[] prescriptionModels) {
        this.prescriptionModels = prescriptionModels;
    }

    @Override
    public final PrescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = new ConstraintLayout(parent.getContext());
        constraintLayout.setBackground(Shape.createRoundDrawable(Dimen.r12, Color.getBackgroundColor()));
        constraintLayout.setElevation(Dimen.elevation);
        constraintLayout.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m32, Dimen.m32, Dimen.m32, -1));
        return new PrescriptionHolder(constraintLayout);
    }

    @Override
    public final void onBindViewHolder(final PrescriptionHolder holder, int position) {
        PrescriptionModel prescriptionModel = prescriptionModels[position];
        holder.setDoctorName(prescriptionModel.getDoctorName());
        holder.setDate(prescriptionModel.getDate());
    }


    @Override
    public final int getItemCount() {
        return prescriptionModels.length;
    }


}

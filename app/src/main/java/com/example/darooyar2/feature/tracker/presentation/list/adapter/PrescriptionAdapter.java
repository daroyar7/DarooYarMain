package com.example.darooyar2.feature.tracker.presentation.list.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.example.darooyar2.feature.tracker.presentation.add.AddPrescriptionFragment;
import com.example.darooyar2.them.Color;
import com.example.darooyar2.them.Dimen;
import com.example.darooyar2.them.Param;
import com.example.darooyar2.them.Shape;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionHolder> {

    private ArrayList<PrescriptionModel> prescriptionModels;
    private ContainerActivity containerActivity;

    public PrescriptionAdapter(ArrayList<PrescriptionModel> prescriptionModels, ContainerActivity containerActivity) {
        this.prescriptionModels = prescriptionModels;
        this.containerActivity = containerActivity;
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
        PrescriptionModel prescriptionModel = prescriptionModels.get(position);
        holder.setDoctorName(prescriptionModel.getDoctorName());
        holder.setDate(prescriptionModel.getDate());
        holder.deleteClicked(view -> {
            prescriptionModels.remove(prescriptionModel);
            PrescriptionQueryImp.getInstance(view.getContext()).removePrescription(prescriptionModel);
            notifyItemRemoved(position);
        });

        holder.editClicked(view -> {
            AddPrescriptionFragment addPrescriptionFragment = new AddPrescriptionFragment();
            containerActivity.pushFragment(addPrescriptionFragment, AddPrescriptionFragment.class.getName());
        });

    }


    @Override
    public final int getItemCount() {
        return prescriptionModels.size();
    }


}

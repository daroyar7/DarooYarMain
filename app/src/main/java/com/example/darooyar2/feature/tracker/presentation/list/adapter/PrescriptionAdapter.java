package com.example.darooyar2.feature.tracker.presentation.list.adapter;

import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.feature.tracker.presentation.detail.PrescriptionDetailFragment;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.Shape;

import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.example.darooyar2.feature.tracker.presentation.put.PutPrescriptionFragment;

import org.json.JSONException;

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
        constraintLayout.setBackground(Shape.createRoundDrawable(Dimen.r12, Color.getOnSecondaryColor()));
        constraintLayout.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m32, Dimen.m40, Dimen.m40, -1));
        return new PrescriptionHolder(constraintLayout);
    }

    @Override
    public final void onBindViewHolder(final PrescriptionHolder holder, int position) {
        PrescriptionModel prescriptionModel = prescriptionModels.get(position);
        holder.setDoctorName(prescriptionModel.getDoctorName());
        holder.setDate(prescriptionModel.getDate());
        holder.deleteClicked(view -> {
            prescriptionModels.remove(prescriptionModel);
            try {
                PrescriptionQueryImp.getInstance(view.getContext()).deletePrescription(prescriptionModel);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            notifyItemRemoved(position);
        });

        holder.editClicked(view -> {
            PutPrescriptionFragment putPrescriptionFragment = new PutPrescriptionFragment();
            putPrescriptionFragment.setDefaultPrescription(prescriptionModel);
            putPrescriptionFragment.setListener((prescriptionModel1) -> editItem(prescriptionModel1));
            containerActivity.pushFragment(putPrescriptionFragment, PutPrescriptionFragment.class.getName());
        });

        holder.parentClicked(view -> {
            PrescriptionDetailFragment prescriptionDetailFragment = new PrescriptionDetailFragment();
            prescriptionDetailFragment.setPrescriptionModel(prescriptionModel);
            containerActivity.pushFragment(prescriptionDetailFragment, PrescriptionDetailFragment.class.getName());
        });

    }

    public void addItem(PrescriptionModel prescriptionModel) {
        this.prescriptionModels.add(0, prescriptionModel);
        notifyDataSetChanged();
    }

    public void editItem(PrescriptionModel prescriptionModel) {
        int index = prescriptionModels.indexOf(prescriptionModel);
        prescriptionModels.remove(prescriptionModel);
        prescriptionModels.add(index, prescriptionModel);
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return prescriptionModels.size();
    }


}

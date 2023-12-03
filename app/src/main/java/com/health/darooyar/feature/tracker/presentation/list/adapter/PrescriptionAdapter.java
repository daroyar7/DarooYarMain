package com.health.darooyar.feature.tracker.presentation.list.adapter;

import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.health.darooyar.container.ContainerActivity;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionModel;
import com.health.darooyar.feature.tracker.presentation.detail.PrescriptionDetailFragment;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.Shape;

import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.health.darooyar.feature.tracker.presentation.put.PutPrescriptionFragment;

import org.json.JSONException;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionHolder> {

    private ArrayList<PrescriptionModel> prescriptionModels;
    private ContainerActivity containerActivity;
    private ItemChangedListeners listener;


    public PrescriptionAdapter(ArrayList<PrescriptionModel> prescriptionModels, ContainerActivity containerActivity,ItemChangedListeners listener) {
        this.prescriptionModels = prescriptionModels;
        this.containerActivity = containerActivity;
        this.listener=listener;
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
            try {
                prescriptionModels.remove(prescriptionModel);
                ArrayList<MedicineModel> medicineModels = MedicineQueryImp.getInstance(view.getContext()).getMedicines(prescriptionModel.getId());
                for (int i = 0; i < medicineModels.size(); i++) {
                    MedicineQueryImp.getInstance(view.getContext()).deleteMedicine(medicineModels.get(i));
                }
                PrescriptionQueryImp.getInstance(view.getContext()).deletePrescription(prescriptionModel);
                listener.onItemChanged(getItemCount());
                notifyItemRemoved(position);
            } catch (JSONException ignored) {
            }
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
        listener.onItemChanged(getItemCount());
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

    public interface ItemChangedListeners{
        void onItemChanged(int itemsCount);
    }

}

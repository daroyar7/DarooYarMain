package com.health.darooyar.feature.tracker.presentation.detail.Medicine.adapter;

import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.health.darooyar.container.ContainerActivity;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.health.darooyar.feature.tracker.presentation.detail.Medicine.add.PutMedicineFragment;
import com.health.darooyar.feature.tracker.presentation.list.adapter.PrescriptionAdapter;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.Shape;

import org.json.JSONException;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {

    private ArrayList<MedicineModel> medicineModels;
    private ContainerActivity containerActivity;
    private long prescriptionId;
    private PrescriptionAdapter.ItemChangedListeners listeners;

    public MedicineAdapter(ArrayList<MedicineModel> medicineModels, ContainerActivity containerActivity, long prescriptionId, PrescriptionAdapter.ItemChangedListeners listeners) {
        this.medicineModels = medicineModels;
        this.containerActivity = containerActivity;
        this.prescriptionId = prescriptionId;
        this.listeners = listeners;
    }

    @Override
    public final MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = new ConstraintLayout(parent.getContext());
        constraintLayout.setBackground(Shape.createRoundDrawable(Dimen.r12, Color.getOnSecondaryColor()));
        constraintLayout.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m32, Dimen.m40, Dimen.m40, -1));
        return new MedicineHolder(constraintLayout);
    }

    @Override
    public final void onBindViewHolder(final MedicineHolder holder, int position) {
        MedicineModel medicineModel = medicineModels.get(position);
        holder.setTvName(medicineModel.getName());
        holder.setTvDuration(medicineModel.getDurationNumber() + " " + medicineModel.getDurationUnit());
        holder.setTvDate(medicineModel.getStartDate() + "     " + medicineModel.getStartTime());
        holder.deleteClicked(view -> {
            try {
                MedicineQueryImp.getInstance(view.getContext()).deleteMedicine(medicineModel);
                medicineModels.remove(medicineModel);
                notifyItemRemoved(position);
                listeners.onItemChanged(getItemCount());
            } catch (JSONException e) {
            }
        });

        holder.editClicked(view -> {
            PutMedicineFragment putMedicineFragment = new PutMedicineFragment();
            putMedicineFragment.setDefaultModel(medicineModel);
            putMedicineFragment.setPrescriptionId(prescriptionId);
            putMedicineFragment.setListener(this::editItem);
            containerActivity.pushFragment(putMedicineFragment, PutMedicineFragment.class.getName());
        });

        holder.itemClicked(view -> {
            PutMedicineFragment putMedicineFragment = new PutMedicineFragment();
            putMedicineFragment.setDefaultModel(medicineModel);
            putMedicineFragment.setPrescriptionId(prescriptionId);
            putMedicineFragment.setReadOnly(medicineModel.getStartDate(), medicineModel.getStartTime());
            containerActivity.pushFragment(putMedicineFragment, PutMedicineFragment.class.getName());
        });
    }

    public void addItem(MedicineModel medicineModel) {
        medicineModels.add(0, medicineModel);
        notifyDataSetChanged();
        listeners.onItemChanged(getItemCount());
    }

    public void editItem(MedicineModel medicineModel) {
        int index = medicineModels.indexOf(medicineModel);
        medicineModels.remove(medicineModel);
        medicineModels.add(index, medicineModel);
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return medicineModels.size();
    }


}

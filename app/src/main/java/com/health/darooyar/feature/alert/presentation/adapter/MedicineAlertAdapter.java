package com.health.darooyar.feature.alert.presentation.adapter;

import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.health.darooyar.container.AppLoader;
import com.health.darooyar.container.ContainerActivity;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.feature.tracker.presentation.detail.Medicine.add.PutMedicineFragment;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.Shape;

import java.util.ArrayList;
import java.util.Calendar;

public class MedicineAlertAdapter extends RecyclerView.Adapter<MedicineHolder> {

    private ArrayList<MedicineModel> medicineModels;
    private ContainerActivity containerActivity;

    public MedicineAlertAdapter(ArrayList<MedicineModel> medicineModels, ContainerActivity containerActivity) {
        this.medicineModels = medicineModels;
        this.containerActivity = containerActivity;
    }

    @Override
    public final MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = new ConstraintLayout(parent.getContext());
        constraintLayout.setBackground(Shape.createRoundDrawable(Dimen.r12, Color.getOnSecondaryColor()));
        constraintLayout.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m16, Dimen.m40, Dimen.m40, Dimen.m16));
        return new MedicineHolder(constraintLayout);
    }

    @Override
    public final void onBindViewHolder(final MedicineHolder holder, int position) {
        MedicineModel medicineModel = medicineModels.get(position);
        holder.setTvName(medicineModel.getName());
        long timeMustUsed = medicineModel.getTimeMustUsed();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMustUsed);
        holder.setTvDate(calendar.get(Calendar.HOUR_OF_DAY) + " : " + calendar.get(Calendar.MINUTE));

        holder.parent.setOnClickListener(view -> {
            PutMedicineFragment putMedicineFragment = new PutMedicineFragment();
            putMedicineFragment.setDefaultModel(medicineModel);
            putMedicineFragment.setListener(model -> {
                holder.getCheckBox().setChecked(true);
                holder.getCheckBox().setEnabled(false);
            });
            putMedicineFragment.setPrescriptionId(medicineModel.getPrescriptionId());
            putMedicineFragment.setReadOnly(medicineModel.getStartDate(), calendar.get(Calendar.HOUR_OF_DAY) + " : " + calendar.get(Calendar.MINUTE));
            containerActivity.pushFragment(putMedicineFragment, PutMedicineFragment.class.getName());
        });

        holder.getCheckBox().setChecked(false);

        if (timeMustUsed + 5 * 60 * 1000 > System.currentTimeMillis()) {
            holder.getCheckBox().setChecked(false);
            holder.getCheckBox().setEnabled(false);
        } else {
            if (AppLoader.sharedPreferences.getBoolean(timeMustUsed + medicineModel.hashCode() + "", false)) {
                holder.getCheckBox().setChecked(true);
                holder.getCheckBox().setEnabled(false);
            } else {
                holder.getCheckBox().setEnabled(true);
                holder.getCheckBox().setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) {
                        holder.getCheckBox().setEnabled(false);
                        holder.takingMedicine(timeMustUsed, medicineModel.hashCode());
                    }
                });
            }
        }
    }


    @Override
    public final int getItemCount() {
        return medicineModels.size();
    }
}

package com.example.darooyar2.feature.alert.presentation.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.common.solarDate.NewPersianDate;
import com.example.darooyar2.container.AppLoader;
import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineModel;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.Shape;
import com.example.darooyar2.utils.PersianDate;

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
        constraintLayout.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m32, Dimen.m40, Dimen.m40, -1));
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

        if (timeMustUsed + 5 * 60 * 1000 > System.currentTimeMillis()) {
            holder.getCheckBox().setChecked(false);
            holder.getCheckBox().setEnabled(false);
        }else {
            if (position == 0){
                Log.i("TAG", "takingMedicine onBindViewHolder: " +timeMustUsed);
            }
            if (AppLoader.sharedPreferences.getBoolean(timeMustUsed + medicineModel.hashCode() + "", false)){
                holder.getCheckBox().setChecked(true);
                holder.getCheckBox().setEnabled(false);
            }else{
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

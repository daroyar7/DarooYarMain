package com.example.darooyar2.feature.alert.presentation;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.feature.alert.presentation.adapter.MedicineAlertAdapter;
import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineModel;
import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Param;

import java.util.ArrayList;

public class AlertFragment extends BaseFragment {
    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getOutlineColor());

        ArrayList<MedicineModel> medicineModels = MedicineQueryImp.getInstance(activity).getMedicinesToday();

        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new MedicineAlertAdapter(medicineModels, activity));
        parent.addView(recyclerView, Param.consParam(0, 0, 0, 0, 0, 0));

        return parent;
    }
}

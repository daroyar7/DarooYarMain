package com.health.darooyar.feature.alert.presentation;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.feature.alert.presentation.adapter.MedicineAlertAdapter;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;

import java.util.ArrayList;

public class AlertFragment extends BaseFragment {
    @Override
    protected ViewGroup onViewFragmentCreate() {
        appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);
        parent.setBackgroundColor(Color.getBackgroundColor());

        ArrayList<MedicineModel> medicineModels = MedicineQueryImp.getInstance(activity).getMedicinesToday();

        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new MedicineAlertAdapter(medicineModels, activity));
        parent.addView(recyclerView, Param.consParam(0, 0, 0, 0, 0, 0));

        return parent;
    }
}

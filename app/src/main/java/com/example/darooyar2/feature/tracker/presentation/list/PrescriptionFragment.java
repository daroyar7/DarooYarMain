package com.example.darooyar2.feature.tracker.presentation.list;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.R;
import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.example.darooyar2.feature.tracker.presentation.list.adapter.PrescriptionAdapter;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.Text;
import com.example.darooyar2.theme.component.PersionDateTime.date.DatePickerDialog;
import com.example.darooyar2.theme.component.PersionDateTime.utils.PersianCalendar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PrescriptionFragment extends BaseFragment {

    private RecyclerView recyclerView;
    public PrescriptionAdapter adapter;

    @Override
    protected ViewGroup onViewFragmentCreate() {
        appTheme.setUpStatusBar(activity,android.graphics.Color.parseColor("#f5f5f5"),false);
        parent.setBackgroundColor(android.graphics.Color.parseColor("#f5f5f5"));//android.graphics.Color.parseColor("#f5f5f5") //Color.getSurfaceColor()

        PrescriptionEvent prescriptionEvent = new PrescriptionEvent(this);

        ExtendedFloatingActionButton fabButton = new ExtendedFloatingActionButton(activity);
        fabButton.setText(Text.ADD_PRESCRIPTION);
        fabButton.setTypeface(appTheme.getRegularTypeface());
        fabButton.setOnClickListener(prescriptionEvent);
        fabButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        fabButton.setIcon(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_add, null));
        parent.addView(fabButton, Param.consParam(appTheme.getAf(400), appTheme.getAf(165), -1, -1, 0, 0, -1, -1, Dimen.m40, Dimen.m40));

//        PersianCalendar persianCalendar = new PersianCalendar();
//        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
//
//                },
//                persianCalendar.getPersianYear(),
//                persianCalendar.getPersianMonth(),
//                persianCalendar.getPersianDay()
//        );
//        datePickerDialog.show(activity.getFragmentManager(),null);

        recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter=new PrescriptionAdapter(PrescriptionQueryImp.getInstance(activity).getPrescriptions(), activity);
        recyclerView.setAdapter(adapter);
        parent.addView(recyclerView, Param.consParam(0, 0, 0, 0, 0, 0));

        return parent;
    }


    @Override
    protected void onHideChange(boolean isHide) {
        if (!isHide) {
            recyclerView.setAdapter(new PrescriptionAdapter(PrescriptionQueryImp.getInstance(activity).getPrescriptions(), activity));
        }
    }
}

package com.example.darooyar2.feature.tracker.presentation.list;

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
import com.example.darooyar2.them.Dimen;
import com.example.darooyar2.them.Param;
import com.example.darooyar2.them.Text;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PrescriptionFragment extends BaseFragment {

    @Override
    protected ViewGroup onViewFragmentCreate() {

        PrescriptionEvent prescriptionEvent = new PrescriptionEvent(this);

        ExtendedFloatingActionButton fabButton = new ExtendedFloatingActionButton(activity);
        fabButton.setText(Text.ADD_PRESCRIPTION);
        fabButton.setTypeface(appTheme.getRegularTypeface());
        fabButton.setOnClickListener(prescriptionEvent);
        fabButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        fabButton.setIcon(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_add, null));
        parent.addView(fabButton, Param.consParam(appTheme.getAf(400), appTheme.getAf(165), -1, -1, 0, 0, -1, -1, Dimen.m40, Dimen.m40));

        PrescriptionQueryImp.getInstance(activity).addPrescription(new PrescriptionModel("علی " + System.currentTimeMillis(), "1380/5/5"));

        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PrescriptionAdapter(PrescriptionQueryImp.getInstance(activity).getPrescriptions()));
        parent.addView(recyclerView, Param.consParam(0, 0, 0, 0, 0, 0));

        return parent;
    }

}

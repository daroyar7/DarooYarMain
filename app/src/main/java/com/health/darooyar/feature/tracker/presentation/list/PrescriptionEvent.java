package com.health.darooyar.feature.tracker.presentation.list;

import android.view.View;

import com.health.darooyar.feature.tracker.presentation.put.PutPrescriptionFragment;

public class PrescriptionEvent implements View.OnClickListener {

    private PrescriptionFragment prescriptionFragment;

    public PrescriptionEvent(PrescriptionFragment prescriptionFragment){
        this.prescriptionFragment = prescriptionFragment;
    }

    @Override
    public void onClick(View view) {
        PutPrescriptionFragment putPrescriptionFragment=new PutPrescriptionFragment();
        putPrescriptionFragment.setListener((prescriptionModel)->prescriptionFragment.adapter.addItem(prescriptionModel));
        prescriptionFragment.activity.pushFragment(putPrescriptionFragment, PutPrescriptionFragment.class.getName());
    }
}

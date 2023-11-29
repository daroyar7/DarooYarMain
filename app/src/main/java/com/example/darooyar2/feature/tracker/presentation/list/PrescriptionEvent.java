package com.example.darooyar2.feature.tracker.presentation.list;

import android.view.View;

import com.example.darooyar2.feature.tracker.presentation.put.PutPrescriptionFragment;

public class PrescriptionEvent implements View.OnClickListener {

    private PrescriptionFragment prescriptionFragment;

    public PrescriptionEvent(PrescriptionFragment prescriptionFragment){
        this.prescriptionFragment = prescriptionFragment;
    }

    @Override
    public void onClick(View view) {
        PutPrescriptionFragment putPrescriptionFragment=new PutPrescriptionFragment();
        putPrescriptionFragment.setListener((prescriptionModel)->prescriptionFragment.adapter.reloadItems(prescriptionFragment.activity ,prescriptionModel));
        prescriptionFragment.activity.pushFragment(putPrescriptionFragment, PutPrescriptionFragment.class.getName());
    }
}

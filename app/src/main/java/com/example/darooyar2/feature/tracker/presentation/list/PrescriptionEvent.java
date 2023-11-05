package com.example.darooyar2.feature.tracker.presentation.list;

import android.view.View;

import com.example.darooyar2.feature.tracker.presentation.add.AddPrescriptionFragment;

public class PrescriptionEvent implements View.OnClickListener {

    private PrescriptionFragment prescriptionFragment;

    public PrescriptionEvent(PrescriptionFragment prescriptionFragment){
        this.prescriptionFragment = prescriptionFragment;
    }

    @Override
    public void onClick(View view) {
        prescriptionFragment.activity.pushFragment(new AddPrescriptionFragment(), AddPrescriptionFragment.class.getName());
    }
}

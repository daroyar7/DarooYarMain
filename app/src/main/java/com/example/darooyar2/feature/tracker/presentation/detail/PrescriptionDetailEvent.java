package com.example.darooyar2.feature.tracker.presentation.detail;

import android.view.View;

import com.example.darooyar2.feature.tracker.presentation.list.PrescriptionFragment;

public class PrescriptionDetailEvent implements View.OnClickListener {
    PrescriptionDetailFragment fragment;
    public PrescriptionDetailEvent(PrescriptionDetailFragment fragment) {
        this.fragment=fragment;
    }

    @Override
    public void onClick(View v) {

    }
}

package com.example.darooyar2.feature.tracker.prescription.presentation;

import android.view.ViewGroup;

import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.them.Color;

public class PrescriptionFragment extends BaseFragment {

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getErrorColor());
        return parent;
    }

}

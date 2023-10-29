package com.example.darooyar2.feature.tracker.prescription.presentation;

import android.view.ViewGroup;

import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.them.Color;
import com.example.darooyar2.them.Param;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PrescriptionFragment extends BaseFragment {

    @Override
    protected ViewGroup onViewFragmentCreate() {

        ExtendedFloatingActionButton fabButton = new ExtendedFloatingActionButton(activity);
        fabButton.setText("click");
        parent.addView(fabButton, Param.consParam(appTheme.getAf(360), appTheme.getAf(165), -1, -1, 0, 0, -1, -1, appTheme.getAf(50), appTheme.getAf(50)));

        return parent;
    }

}

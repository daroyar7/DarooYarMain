package com.example.darooyar2.feature.alert.presentation;

import android.view.ViewGroup;

import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.theme.Color;

public class AlertFragment extends BaseFragment {
    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getOutlineColor());
        return parent;
    }
}

package com.example.darooyar2.feature.tracker.presentation.addPrescription;

import android.view.View;

import com.example.darooyar2.them.component.DatePickerView;

public class AddPrescriptionEvent implements View.OnClickListener {
    private AddPrescriptionFragment fragment;

    public AddPrescriptionEvent(AddPrescriptionFragment fragment) {
        this.fragment=fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==fragment.idBtnSubmit){

        } else if (v.getId() == DatePickerView.idTextField) {

        }
    }
}

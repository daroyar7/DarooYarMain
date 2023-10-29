package com.example.darooyar2.feature.tracker.presentation.addPrescription;

import android.view.View;

import com.example.darooyar2.them.component.datePicker.DatePickerView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPrescriptionEvent implements View.OnClickListener {
    private AddPrescriptionFragment fragment;

    public AddPrescriptionEvent(AddPrescriptionFragment fragment) {
        this.fragment=fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==fragment.idBtnSubmit){

        } else if (v.getId() == DatePickerView.idTextField) {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                String date = new SimpleDateFormat("MM-dd-yyy", Locale.getDefault()).format(new Date(selection));
                ((TextInputEditText)v).setText(MessageFormat.format("Selected Date: {0}", date));
            });
            materialDatePicker.show(fragment.activity.getSupportFragmentManager(), "tag");
        }
    }
}

package com.example.darooyar2.feature.tracker.presentation.addPrescription;

import android.view.View;
import android.widget.TextView;

import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.example.darooyar2.them.component.DatePickerView;
import com.example.darooyar2.them.component.FormFieldView;

public class AddPrescriptionEvent implements View.OnClickListener {
    private AddPrescriptionFragment fragment;

    public AddPrescriptionEvent(AddPrescriptionFragment fragment) {
        this.fragment=fragment;
    }

    @Override
    public void onClick(View v) {
        String date=((DatePickerView)(fragment.parent.findViewById(fragment.idFieldDate))).getText();
        String doctorName=((FormFieldView)fragment.parent.findViewById(fragment.idFieldDoctorName)).getText();
        if (v.getId() == fragment.idBtnSubmit) {
            boolean isDateEmpty = date.isEmpty();
            boolean isDoctorNameEmpty = doctorName.isEmpty();

            ((DatePickerView) fragment.parent.findViewById(fragment.idFieldDate)).setError(isDateEmpty ? "لطفا تاریخ را وارد کنید." : "");
            ((FormFieldView) fragment.parent.findViewById(fragment.idFieldDoctorName)).setError(isDoctorNameEmpty ? "لطفا نام را وارد کنید." : "");

            if (!isDateEmpty && !isDoctorNameEmpty) {
                PrescriptionQueryImp.getInstance(fragment.activity).addPrescription(new PrescriptionModel(doctorName, date));
                fragment.onBackPressed();
            }
        }
    }
}

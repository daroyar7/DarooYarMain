package com.example.darooyar2.feature.tracker.presentation.put;

import android.view.View;

import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.example.darooyar2.theme.component.DatePickerView;
import com.example.darooyar2.theme.component.FormFieldView;

import org.json.JSONException;

public class PutPrescriptionEvent implements View.OnClickListener {

    private PutPrescriptionFragment fragment;
    private PrescriptionModel prescriptionModel;

    public PutPrescriptionEvent(PutPrescriptionFragment fragment, PrescriptionModel prescriptionModel) {
        this.fragment=fragment;
        if (prescriptionModel == null)
            this.prescriptionModel = new PrescriptionModel("", "");
        else
            this.prescriptionModel = prescriptionModel;
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
                prescriptionModel.setDoctorName(doctorName);
                prescriptionModel.setDate(date);
                try {
                    PrescriptionQueryImp.getInstance(fragment.activity).putPrescription(prescriptionModel);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                fragment.onBackPressed();
            }
        }
    }
}

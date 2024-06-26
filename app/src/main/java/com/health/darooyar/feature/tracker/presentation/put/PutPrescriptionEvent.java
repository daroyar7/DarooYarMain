package com.health.darooyar.feature.tracker.presentation.put;

import android.util.Log;
import android.view.View;

import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionModel;
import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.health.darooyar.theme.component.DatePickerView;
import com.health.darooyar.theme.component.FormFieldView;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PutPrescriptionEvent implements View.OnClickListener {

    private PutPrescriptionFragment fragment;
    private PrescriptionModel prescriptionModel;

    public PutPrescriptionEvent(PutPrescriptionFragment fragment, PrescriptionModel prescriptionModel) {
        this.fragment = fragment;
        if (prescriptionModel == null)
            this.prescriptionModel = new PrescriptionModel("", "", "");
        else
            this.prescriptionModel = prescriptionModel;
    }

    @Override
    public void onClick(View v) {
        String date = ((DatePickerView) (fragment.parent.findViewById(fragment.idFieldDate))).getText();
        String doctorName = ((FormFieldView) fragment.parent.findViewById(fragment.idFieldDoctorName)).getText();
        String prescriptionName = ((FormFieldView) fragment.parent.findViewById(fragment.idFieldPrescriptionName)).getText();
        if (v.getId() == fragment.idBtnSubmit) {
            boolean isDateEmpty = date.isEmpty();

            ((DatePickerView) fragment.parent.findViewById(fragment.idFieldDate)).setError(isDateEmpty ? "لطفا تاریخ را وارد کنید." : "");

            if (!isDateEmpty) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    dateFormat.parse(date);

                    prescriptionModel.setPrescriptionName(prescriptionName);
                    prescriptionModel.setDoctorName(doctorName);
                    prescriptionModel.setDate(date);
                    try {
                        PrescriptionQueryImp.getInstance(fragment.activity).putPrescription(prescriptionModel);
                        fragment.itemAdded(prescriptionModel);
                    } catch (JSONException e) {
                        Log.i("TAG", "onClick: " + e);
                    }
                    fragment.onBackPressed();
                }catch (ParseException e){
                    ((DatePickerView) fragment.parent.findViewById(fragment.idFieldDate)).setError("لطفا تاریخ صحیح را وارد کنید.");
                }
            }
        }
    }

}

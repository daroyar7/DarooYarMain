package com.example.darooyar2.feature.tracker.presentation.detail.Medicine.add;

import android.util.Log;
import android.view.View;

import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineModel;
import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.example.darooyar2.theme.component.DatePickerView;
import com.example.darooyar2.theme.component.FormFieldView;
import com.example.darooyar2.theme.component.TimePickerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class PutMedicineEvent implements View.OnClickListener {
    PutMedicineFragment fragment;

    MedicineModel medicineModel;
    int duration = 1;
    String durationUnit = durationUnits.get(0);
    protected static ArrayList<String> durationUnits = new ArrayList<>(Arrays.asList("ساعت", "روز", "هفته"));

    public PutMedicineEvent(PutMedicineFragment fragment, MedicineModel model , long prescriptionId) {
        this.fragment = fragment;
        if (medicineModel == null)
            this.medicineModel = new MedicineModel("", 1, "", "", "",prescriptionId);
        else
            this.medicineModel = model;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragment.idBtnSubmit) {
            String name = ((FormFieldView) fragment.parent.findViewById(fragment.idFormNameView)).getText();
            String detail = ((FormFieldView) fragment.parent.findViewById(fragment.idFormDetailView)).getText();
            String date = ((DatePickerView) fragment.parent.findViewById(fragment.idFieldDate)).getText();
            String time = ((TimePickerView) fragment.parent.findViewById(fragment.idFieldTime)).getText();
            Log.i("TAG", "onClick: "+time);

            boolean isNameFieldEmpty = name.isEmpty();
            boolean isDateFieldEmpty = date.isEmpty();
            boolean isTimeFieldEmpty = time.isEmpty();

            ((FormFieldView) fragment.parent.findViewById(fragment.idFormNameView)).setError(isNameFieldEmpty ? "لطفا نام را وارد کنید." : "");
            ((DatePickerView) fragment.parent.findViewById(fragment.idFieldDate)).setError(isNameFieldEmpty ? "لطفا تاریخ را وارد کنید." : "");
            ((TimePickerView) fragment.parent.findViewById(fragment.idFieldTime)).setError(isNameFieldEmpty ? "لطفا زمان را وارد کنید." : "");

            if (!isNameFieldEmpty && !isTimeFieldEmpty && !isDateFieldEmpty) {
                medicineModel.setName(name);
                medicineModel.setDetail(detail == null ? "" : detail);
                medicineModel.setDurationNumber(duration);
                medicineModel.setDurationUnit(durationUnit);
                medicineModel.setStartDate(date);
                medicineModel.setStartTime(time);
                medicineModel.setPrescriptionId(medicineModel.getPrescriptionId());
                try {
                    MedicineQueryImp.getInstance(fragment.activity).putMedicine(medicineModel);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                fragment.itemAdded(medicineModel);
                fragment.onBackPressed();
            }
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }
}

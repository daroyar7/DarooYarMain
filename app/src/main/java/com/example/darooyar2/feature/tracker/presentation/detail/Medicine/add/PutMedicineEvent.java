package com.example.darooyar2.feature.tracker.presentation.detail.Medicine.add;

import android.view.View;

import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineModel;
import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.example.darooyar2.theme.component.FormFieldView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class PutMedicineEvent implements View.OnClickListener {
    PutMedicineFragment fragment;

    MedicineModel medicineModel;
    int duration = 1;
    String durationUnit = durationUnits.get(0);
    protected static ArrayList<String> durationUnits = new ArrayList<>(Arrays.asList("ساعت", "روز", "هفته", "ماه", "سال"));

    public PutMedicineEvent(PutMedicineFragment fragment, MedicineModel model) {
        this.fragment = fragment;
        if (medicineModel == null)
            this.medicineModel = new MedicineModel("", 1, "", "", "");
        else
            this.medicineModel = model;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragment.idBtnSubmit) {
            String name = ((FormFieldView) fragment.parent.findViewById(fragment.idFormNameView)).getText();
            String detail = ((FormFieldView) fragment.parent.findViewById(fragment.idFormDetailView)).getText();
            String date = ((FormFieldView) fragment.parent.findViewById(fragment.idFieldDate)).getText();
            String time = ((FormFieldView) fragment.parent.findViewById(fragment.idFieldTime)).getText();

            boolean isNameFieldEmpty = name.isEmpty();
            boolean isDateFieldEmpty = date.isEmpty();
            boolean isTimeFieldEmpty = time.isEmpty();

            ((FormFieldView) fragment.parent.findViewById(fragment.idFormNameView)).setError(isNameFieldEmpty ? "لطفا نام را وارد کنید." : "");
            ((FormFieldView) fragment.parent.findViewById(fragment.idFieldDate)).setError(isNameFieldEmpty ? "لطفا تاریخ را وارد کنید." : "");
            ((FormFieldView) fragment.parent.findViewById(fragment.idFieldTime)).setError(isNameFieldEmpty ? "لطفا زمان را وارد کنید." : "");

            if (!isNameFieldEmpty && !isTimeFieldEmpty && !isDateFieldEmpty) {
                medicineModel.setName(name);
                medicineModel.setDetail(detail == null ? "" : detail);
                medicineModel.setDurationNumber(duration);
                medicineModel.setDurationUnit(durationUnit);
                medicineModel.setStartDate(date);
                medicineModel.setStartTime(time);
                try {
                    MedicineQueryImp.getInstance(fragment.activity).putMedicine(medicineModel);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
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

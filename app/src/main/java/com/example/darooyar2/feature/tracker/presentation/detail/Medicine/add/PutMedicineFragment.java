package com.example.darooyar2.feature.tracker.presentation.detail.Medicine.add;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darooyar2.R;
import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.feature.tracker.data.database.medicine.MedicineModel;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.component.DatePickerView;
import com.example.darooyar2.theme.component.FormFieldView;
import com.example.darooyar2.theme.component.NumberPicker.DurationPicker;
import com.example.darooyar2.theme.component.TimePickerView;
import com.example.darooyar2.theme.component.VoiceDescriptionView;
import com.google.android.material.button.MaterialButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class PutMedicineFragment extends BaseFragment {
    protected int idBtnSubmit = 5455;
    protected int idFormNameView = 8544;
    protected int idFormDetailView = 554;
    protected int idFieldDate = 8084;
    protected int idFieldTime = 541;
    protected int idSpinner = 4587;
    protected int idDurationPicker = 58845;
    private MedicineModel medicineModel;
    private long prescriptionId;
    private AddDataListener listener;
    private boolean isRecorder = true;
    private String txtDefaultDate = "", txtDefaultTime = "";


    public void setListener(AddDataListener listener) {
        this.listener = listener;
    }

    public void setReadOnly(String txtDefaultDate, String txtDefaultTime) {
        this.txtDefaultDate = txtDefaultDate;
        this.txtDefaultTime = txtDefaultTime;
        this.isRecorder = false;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setDefaultModel(MedicineModel medicineModel) {
        this.medicineModel = medicineModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getBackgroundColor());
        AppTheme.getInstance().setUpStatusBar(activity, Color.getBackgroundColor(), false);

        PutMedicineEvent event = new PutMedicineEvent(this, medicineModel, prescriptionId);

        FormFieldView formNameView = new FormFieldView(activity);
        formNameView.setId(idFormNameView);
        formNameView.setUp(R.drawable.ic_medicine, "نام دارو", medicineModel == null ? "" : medicineModel.getName());
        parent.addView(formNameView, Param.consParam(-1, -2, 0, 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DatePickerView fieldDate = new DatePickerView(activity);
        fieldDate.setId(idFieldDate);
        fieldDate.setUp(R.drawable.ic_date, "تاریخ شروع", isRecorder ? (medicineModel == null ? "" : medicineModel.getStartDate()) : txtDefaultDate);
        parent.addView(fieldDate, Param.consParam(0, -2, -formNameView.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        TimePickerView fieldTime = new TimePickerView(activity);
        fieldTime.setId(idFieldTime);
        fieldTime.setUp(R.drawable.ic_time, "زمان شروع", isRecorder ? (medicineModel == null ? "" : medicineModel.getStartTime()) : txtDefaultTime);
        parent.addView(fieldTime, Param.consParam(0, -2, -fieldDate.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        // //////////////////////////////////////////////////////
        ImageView icDuration = new ImageView(activity);
        icDuration.setId(65965);
        icDuration.setImageResource(R.drawable.ic_duration);
        icDuration.setColorFilter(Color.getOnBackgroundColor());
        parent.addView(icDuration, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), -fieldTime.getId(), -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        TextView tvDuration = new TextView(activity);
        tvDuration.setText("دوره ی مصرف:");
        tvDuration.setId(6895);
        parent.addView(tvDuration, Param.consParam(-2, -2, icDuration.getId(), -1, -icDuration.getId(), icDuration.getId(), -1, -1, Dimen.m16, -1));

        MaterialSpinner spinner = new MaterialSpinner(activity);
        spinner.setId(idSpinner);
        spinner.setBackground(appTheme.createRoundDrawable(appTheme.getAf(100), Color.getOnPrimaryColor()));
        spinner.setItems(PutMedicineEvent.durationUnits);
        spinner.setGravity(Gravity.CENTER);
        spinner.setSelectedIndex(medicineModel == null ? 0 : (PutMedicineEvent.durationUnits.indexOf(medicineModel.getDurationUnit())));
        spinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            event.setDurationUnit(item);
        });
        parent.addView(spinner, Param.consParam(appTheme.getAf(250), -2, tvDuration.getId(), 0, -1, tvDuration.getId(), -1, Dimen.m40, -1, -1));

        DurationPicker durationPicker = new DurationPicker(activity, medicineModel == null ? 0 : medicineModel.getDurationNumber());
        durationPicker.setId(idDurationPicker);
        durationPicker.setListener(event::setDuration);
        parent.addView(durationPicker, Param.consParam(-2, appTheme.getAf(250), -tvDuration.getId(), -spinner.getId(), -tvDuration.getId(), tvDuration.getId()));
        // /////////////////////

        VoiceDescriptionView voiceDescriptionView = new VoiceDescriptionView(activity, isRecorder);
        voiceDescriptionView.setUp(R.drawable.ic_info, "توضیحات:", medicineModel == null ? "v-" + prescriptionId + "-" + System.currentTimeMillis() : medicineModel.getDetail());
        voiceDescriptionView.setId(idFormDetailView);
        parent.addView(voiceDescriptionView, Param.consParam(-1, -2, -durationPicker.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setOnClickListener(event);
        btnSubmit.setText("ثبت دارو");
        btnSubmit.setId(idBtnSubmit);
        parent.addView(btnSubmit, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        if (!isRecorder) {
            setReadOnly();
            CheckBox checkBoxHsTaken = new CheckBox(activity);
            checkBoxHsTaken.setChecked(false);
            checkBoxHsTaken.setText("dfhrf");
            parent.addView(checkBoxHsTaken, Param.consParam(-2, -2, medicineModel.getDetail().equals("") ? -idDurationPicker : -idFormDetailView,
                    -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));
        }
        return parent;
    }

    public String getDescriptionLink() {
        return ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).getFileName();
    }

    public void itemAdded(MedicineModel model) {
        listener.onAddData(model);
    }

    public interface AddDataListener {
        void onAddData(MedicineModel model);
    }

    private void setReadOnly() {
        ((FormFieldView) parent.findViewById(idFormNameView)).setEnabled(false);
        ((DatePickerView) parent.findViewById(idFieldDate)).setEnabled(false);
        ((TimePickerView) parent.findViewById(idFieldTime)).setEnabled(false);
        ((MaterialSpinner) parent.findViewById(idSpinner)).setEnabled(false);
        ((DurationPicker) parent.findViewById(idDurationPicker)).disable();
        ((MaterialButton) parent.findViewById(idBtnSubmit)).setVisibility(View.GONE);
        if (medicineModel.getDetail().equals(""))
            ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).onDestroyView();
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }
}

package com.health.darooyar.feature.tracker.presentation.detail.Medicine.add;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.darooyar.R;
import com.health.darooyar.container.AppLoader;
import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.component.DatePickerView;
import com.health.darooyar.theme.component.FormFieldView;
import com.health.darooyar.theme.component.NumberPicker.DurationPicker;
import com.health.darooyar.theme.component.TimePickerView;
import com.health.darooyar.theme.component.VoiceDescriptionView;
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
        tvDuration.setTypeface(appTheme.getRegularTypeface());
        tvDuration.setId(6895);
        parent.addView(tvDuration, Param.consParam(-2, -2, icDuration.getId(), -1, -icDuration.getId(), icDuration.getId(), -1, -1, Dimen.m16, -1));

        MaterialSpinner spinner = new MaterialSpinner(activity);
        spinner.setId(idSpinner);
        spinner.setTypeface(appTheme.getRegularTypeface());
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

        VoiceDescriptionView voiceDescriptionView = new VoiceDescriptionView(activity, isRecorder);

        voiceDescriptionView.setUp(R.drawable.ic_info, "توضیحات:", medicineModel == null ? "v-" + prescriptionId + "-" + System.currentTimeMillis() : medicineModel.getDetail());
        voiceDescriptionView.setId(idFormDetailView);
        parent.addView(voiceDescriptionView, Param.consParam(-1, -2, -durationPicker.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setTypeface(appTheme.getMediumTypeface());
        btnSubmit.setOnClickListener(event);
        btnSubmit.setTextSize(0, appTheme.getAf(42));
        btnSubmit.setText("ثبت دارو");
        btnSubmit.setId(idBtnSubmit);
        btnSubmit.setCornerRadius(appTheme.getAf(25));
        parent.addView(btnSubmit, Param.consParam(-1, appTheme.getAf(158), -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        if (!isRecorder) {
            setReadOnly();
            CheckBox checkBoxHsTaken = new CheckBox(activity);
            checkBoxHsTaken.setTypeface(appTheme.getMediumTypeface());
            if (medicineModel.getTimeMustUsed() + 5 * 60 * 1000 > System.currentTimeMillis()) {
                checkBoxHsTaken.setChecked(false);
                checkBoxHsTaken.setEnabled(false);
            } else {
                if (AppLoader.sharedPreferences.getBoolean(medicineModel.getTimeMustUsed() + medicineModel.hashCode() + "", false)) {
                    checkBoxHsTaken.setChecked(true);
                    checkBoxHsTaken.setEnabled(false);
                } else {
                    checkBoxHsTaken.setChecked(false);
                    checkBoxHsTaken.setEnabled(true);
                    checkBoxHsTaken.setOnCheckedChangeListener((compoundButton, b) -> {
                        if (b) {
                            checkBoxHsTaken.setEnabled(false);
                            if (listener != null)
                                listener.onAddData(medicineModel);
                        }
                    });
                }
            }
            checkBoxHsTaken.setText("قرصم را خوردم");
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
        parent.findViewById(idFormNameView).setEnabled(false);
        parent.findViewById(idFieldDate).setEnabled(false);
        parent.findViewById(idFieldTime).setEnabled(false);
        parent.findViewById(idSpinner).setEnabled(false);
        ((DurationPicker) parent.findViewById(idDurationPicker)).disable();
        parent.findViewById(idBtnSubmit).setVisibility(View.GONE);
        if (medicineModel.getDetail().equals(""))
            parent.findViewById(idFormDetailView).setVisibility(View.GONE);
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

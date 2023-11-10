package com.example.darooyar2.feature.tracker.presentation.detail.Medicine.add;

import android.view.Gravity;
import android.view.ViewGroup;
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
import com.google.android.material.button.MaterialButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class PutMedicineFragment extends BaseFragment {
    protected int idBtnSubmit = 5455;
    protected int idFormNameView = 8544;
    protected int idFormDetailView = 554;
    protected int idFieldDate = 8084;
    protected int idFieldTime = 541;
    private MedicineModel medicineModel;
    private long prescriptionId;
    private AddDataListener listener;

    public void setListener(AddDataListener listener) {
        this.listener = listener;
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

        PutMedicineEvent event = new PutMedicineEvent(this, medicineModel , prescriptionId);

//        ImageView imgMedicine = new ImageView(activity);
//        imgMedicine.setImageResource(R.drawable.img_medicine);
//        imgMedicine.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imgMedicine.setId(68);
//        parent.addView(imgMedicine, Param.consParam(-1,(int) (0.4*appTheme.heightPixels) , 0, 0, 0, -1));

        FormFieldView formNameView = new FormFieldView(activity);
        formNameView.setId(idFormNameView);
        formNameView.setUp(R.drawable.ic_medicine, "نام دارو", medicineModel == null ? "" : medicineModel.getName());
        parent.addView(formNameView, Param.consParam(-1, -2, 0, 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        FormFieldView formDetailView = new FormFieldView(activity);
        formDetailView.setUp(R.drawable.ic_info, "توضیحات", medicineModel == null ? "" : medicineModel.getDetail());
        formDetailView.setId(idFormDetailView);
        parent.addView(formDetailView, Param.consParam(-1, -2, -formNameView.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DatePickerView fieldDate = new DatePickerView(activity);
        fieldDate.setId(idFieldDate);
        fieldDate.setUp(R.drawable.ic_date, "تاریخ شروع", medicineModel == null ? "" : medicineModel.getStartDate());
        parent.addView(fieldDate, Param.consParam(0, -2, -formDetailView.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        TimePickerView fieldTime = new TimePickerView(activity);
        fieldTime.setId(idFieldTime);
        fieldTime.setUp(R.drawable.ic_date, "زمان شروع", medicineModel == null ? "" : medicineModel.getStartTime());
        parent.addView(fieldTime, Param.consParam(0, -2, -fieldDate.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DurationPicker durationPicker = new DurationPicker(activity , medicineModel == null ? 0 : medicineModel.getDurationNumber());
        durationPicker.setId(58845);
        durationPicker.setListener(event::setDuration);
        parent.addView(durationPicker, Param.consParam(-2, appTheme.getAf(250), -fieldTime.getId(), 0, 0, -1, Dimen.m24, -1, -1, -1));

        TextView tvDuration = new TextView(activity);
        tvDuration.setText("دوره ی مصرف:");
        tvDuration.setId(6895);
        parent.addView(tvDuration, Param.consParam(-2, -2, durationPicker.getId(), -durationPicker.getId(), 0, durationPicker.getId(), -1, -1, -1, -1));

        MaterialSpinner spinner = new MaterialSpinner(activity);
        spinner.setBackground(appTheme.createRoundDrawable(appTheme.getAf(100), Color.getOnPrimaryColor()));
        spinner.setItems(PutMedicineEvent.durationUnits);
        spinner.setGravity(Gravity.CENTER);
        spinner.setSelectedIndex(medicineModel == null ? 0 : (PutMedicineEvent.durationUnits.indexOf(medicineModel.getDurationUnit())));
        spinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            event.setDurationUnit(item);
        });
        parent.addView(spinner, Param.consParam(appTheme.getAf(250), -2, durationPicker.getId(), 0, -durationPicker.getId(), durationPicker.getId()));

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setOnClickListener(event);
        btnSubmit.setText("ثبت دارو");
        btnSubmit.setId(idBtnSubmit);
        parent.addView(btnSubmit, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        return parent;
    }

    public void itemAdded(MedicineModel model){
        listener.onAddData(model);
    }
    public interface AddDataListener{
        void onAddData(MedicineModel model);
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }
}

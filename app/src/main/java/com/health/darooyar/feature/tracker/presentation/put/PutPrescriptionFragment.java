package com.health.darooyar.feature.tracker.presentation.put;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.health.darooyar.R;
import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionModel;
import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.component.FormFieldView;
import com.health.darooyar.theme.component.DatePickerView;
import com.google.android.material.button.MaterialButton;
import com.health.darooyar.theme.component.Toolbar;

public class PutPrescriptionFragment extends BaseFragment {

    private int baseId = 5464;
    protected final int idFieldDoctorName = baseId + 1;
    protected final int idFieldDate = idFieldDoctorName + 1;
    protected final int idBtnSubmit = idFieldDate + 1;

    private PrescriptionModel prescriptionModel;
    public OnAddItemListener listener;

    public void setListener(OnAddItemListener listener) {
        this.listener = listener;
    }

    public void itemAdded(PrescriptionModel prescriptionModel) {
        listener.onAdd(prescriptionModel);
    }

    public void setDefaultPrescription(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getBackgroundColor());
        AppTheme.getInstance().setUpStatusBar(activity, Color.getBackgroundColor(), false);

        appTheme.setUpStatusBar(activity, Color.toolbarBackground(), true);
        Toolbar toolbar = new Toolbar(activity, prescriptionModel == null ? "نسخه\u200cی جدید" : "ویرایش", Color.getOnSecondaryColor(), Color.toolbarBackground(), android.graphics.Color.parseColor("#383838"));
        toolbar.setId(8457);
        parent.addView(toolbar, Param.consParam(0, -2, 0, 0, 0, -1));


        PutPrescriptionEvent event = new PutPrescriptionEvent(this, prescriptionModel);

        ImageView imgPrescription = new ImageView(activity);
        imgPrescription.setImageResource(R.drawable.image_add_prescription);
//        imgPrescription.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgPrescription.setId(baseId + 468);
        parent.addView(imgPrescription, Param.consParam(-2, (int) (0.4 * appTheme.heightPixels), -toolbar.getId(), 0, 0, -1,-1,appTheme.getAf(100),appTheme.getAf(100),-1));

        FormFieldView fieldDoctorName = new FormFieldView(activity);
        fieldDoctorName.setUp(R.drawable.ic_doctor, "نام پزشک", prescriptionModel == null ? "" : prescriptionModel.getDoctorName());
        fieldDoctorName.setId(idFieldDoctorName);
        parent.addView(fieldDoctorName, Param.consParam(0, -2, -imgPrescription.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DatePickerView fieldDate = new DatePickerView(activity);
        fieldDate.setId(idFieldDate);
        fieldDate.setUp(R.drawable.ic_date, "تاریخ مراجعه", prescriptionModel == null ? "" : prescriptionModel.getDate());
        parent.addView(fieldDate, Param.consParam(0, -2, -fieldDoctorName.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setTypeface(appTheme.getMediumTypeface());
        btnSubmit.setOnClickListener(event);
        btnSubmit.setTextSize(0, appTheme.getAf(42));
        btnSubmit.setText("ثبت نسخه");
        btnSubmit.setId(idBtnSubmit);
        btnSubmit.setCornerRadius(appTheme.getAf(25));
        parent.addView(btnSubmit, Param.consParam(-1, appTheme.getAf(158), -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        return parent;
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }

    public interface OnAddItemListener {
        void onAdd(PrescriptionModel prescriptionModel);
    }
}

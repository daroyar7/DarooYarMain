package com.example.darooyar2.feature.tracker.presentation.put;

import android.app.DatePickerDialog;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.darooyar2.R;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.component.FormFieldView;
import com.example.darooyar2.theme.component.DatePickerView;
import com.example.darooyar2.utils.PersianDate;
import com.google.android.material.button.MaterialButton;

public class PutPrescriptionFragment extends BaseFragment {

    private int baseId = 5464;
    protected final int idFieldDoctorName = baseId + 1;
    protected final int idFieldDate = idFieldDoctorName + 1;
    protected final int idBtnSubmit = idFieldDate + 1;

    private PrescriptionModel prescriptionModel;

    public void setDefaultPrescription(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getBackgroundColor());
        AppTheme.getInstance().setUpStatusBar(activity, Color.getBackgroundColor(), false);

        PutPrescriptionEvent event = new PutPrescriptionEvent(this, prescriptionModel);

        ImageView imgPrescription = new ImageView(activity);
        imgPrescription.setImageResource(R.drawable.image_add_prescription);
        imgPrescription.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgPrescription.setId(baseId + 468);
        parent.addView(imgPrescription, Param.consParam(-1, (int) (0.4 * appTheme.heightPixels), 0, 0, 0, -1));

        FormFieldView fieldDoctorName = new FormFieldView(activity);
        fieldDoctorName.setUp(R.drawable.ic_doctor, "نام پزشک", prescriptionModel == null ? "" : prescriptionModel.getDoctorName());
        fieldDoctorName.setId(idFieldDoctorName);
        parent.addView(fieldDoctorName, Param.consParam(0, -2, -imgPrescription.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DatePickerView fieldDate = new DatePickerView(activity);
        fieldDate.setId(idFieldDate);
        fieldDate.setUp(R.drawable.ic_date, "تاریخ مراجعه", prescriptionModel == null ? "" : prescriptionModel.getDate());
        parent.addView(fieldDate, Param.consParam(0, -2, -fieldDoctorName.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

//        PersianDate.install();
//        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
//                activity,
//                PersianDate.year,
//                PersianDate.month,
//                PersianDate.day
//        );
//        datePickerDialog.show();

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setOnClickListener(event);
        btnSubmit.setText("ثبت نسخه");
        btnSubmit.setId(idBtnSubmit);
        parent.addView(btnSubmit, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        return parent;
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }
}

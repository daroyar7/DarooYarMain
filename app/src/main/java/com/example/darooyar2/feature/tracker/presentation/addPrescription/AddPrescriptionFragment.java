package com.example.darooyar2.feature.tracker.presentation.addPrescription;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.darooyar2.R;
import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.them.AppTheme;
import com.example.darooyar2.them.Color;
import com.example.darooyar2.them.Dimen;
import com.example.darooyar2.them.Param;
import com.example.darooyar2.them.component.FormFieldView;
import com.example.darooyar2.them.component.DatePickerView;
import com.google.android.material.button.MaterialButton;

public class AddPrescriptionFragment extends BaseFragment {

    private int baseId = 5464;
    protected final int idFieldDoctorName = baseId + 1;
    protected final int idFieldDate = idFieldDoctorName + 1;
    protected final int idBtnSubmit = idFieldDate + 1;

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getBackgroundColor());
        AppTheme.getInstance().setUpStatusBar(activity, Color.getBackgroundColor(), false);

        AddPrescriptionEvent event=new AddPrescriptionEvent(this);

        ImageView imgPrescription = new ImageView(activity);
        imgPrescription.setImageResource(R.drawable.image_add_prescription);
        imgPrescription.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgPrescription.setId(baseId + 468);
        parent.addView(imgPrescription, Param.consParam(-2, appTheme.getAf(400), 0, 0, 0, -1));

        FormFieldView fieldDoctorName = new FormFieldView(activity);
        fieldDoctorName.setUp(R.drawable.ic_doctor, "نام پزشک");
        fieldDoctorName.setId(idFieldDoctorName);
        parent.addView(fieldDoctorName, Param.consParam(0, -2, -imgPrescription.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DatePickerView fieldDate = new DatePickerView(activity);
        fieldDate.setId(idFieldDate);
        fieldDate.setUp(R.drawable.ic_date, "تاریخ مراجعه");
        parent.addView(fieldDate ,Param.consParam(0, -2, -fieldDoctorName.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        MaterialButton btnSubmit =new MaterialButton(activity);
        btnSubmit.setOnClickListener(event);
        btnSubmit.setText("ثبت نسخه");
        btnSubmit.setId(idBtnSubmit);
        parent.addView(btnSubmit, Param.consParam(-1 , Dimen.m64 , -1 ,0,0,0, -1 , Dimen.m40, Dimen.m40, Dimen.m40 ));

        return parent;
    }
}

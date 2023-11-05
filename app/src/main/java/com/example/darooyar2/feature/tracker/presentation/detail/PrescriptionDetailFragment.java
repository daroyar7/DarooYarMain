package com.example.darooyar2.feature.tracker.presentation.detail;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.darooyar2.R;
import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.feature.tracker.data.database.prescription.PrescriptionModel;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.Text;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PrescriptionDetailFragment extends BaseFragment {
    private PrescriptionModel prescriptionModel;

    private int idDoctorName = 63515;
    private int idDate = 44754;
    private int idHovaShafi = 6541;

    public void setPrescriptionModel(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate() {
        PrescriptionDetailEvent prescriptionDetailEvent=new PrescriptionDetailEvent(this);

        TextView tvDoctorName = new TextView(activity);
        tvDoctorName.setText("دکتر: " + prescriptionModel.getDoctorName());
        tvDoctorName.setId(idDoctorName);
        parent.addView(tvDoctorName, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        TextView tvDate = new TextView(activity);
        tvDate.setText("تاریخ مراجعه: " + prescriptionModel.getDate());
        tvDate.setId(idDate);
        parent.addView(tvDate, Param.consParam(-2, -2, -tvDoctorName.getId(), -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        TextView tvHovaShafi = new TextView(activity);
        tvHovaShafi.setText("او شفادهنده است!!!");
        tvHovaShafi.setId(idDate);
        parent.addView(tvHovaShafi, Param.consParam(-2, -2, -tvDate.getId(), -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        RecyclerView rvMedicine = new RecyclerView(activity);
        rvMedicine.setVerticalFadingEdgeEnabled(true);
        rvMedicine.setFadingEdgeLength(150);
        rvMedicine.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parent.addView(rvMedicine, Param.consParam(-2, -2,-tvHovaShafi.getId(),0,0,0));

        ExtendedFloatingActionButton fabButton = new ExtendedFloatingActionButton(activity);
        fabButton.setText(Text.ADD_PRESCRIPTION);
        fabButton.setTypeface(appTheme.getRegularTypeface());
        fabButton.setOnClickListener(prescriptionDetailEvent);
        fabButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        fabButton.setIcon(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_add, null));
        parent.addView(fabButton, Param.consParam(appTheme.getAf(400), appTheme.getAf(165), -1, -1, 0, 0, -1, -1, Dimen.m40, Dimen.m40));

        return parent;
    }
}

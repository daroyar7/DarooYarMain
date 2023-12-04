package com.health.darooyar.feature.tracker.presentation.detail;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.health.darooyar.R;
import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineQueryImp;
import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionModel;
import com.health.darooyar.feature.tracker.presentation.detail.Medicine.adapter.MedicineAdapter;
import com.health.darooyar.feature.tracker.presentation.detail.Medicine.add.PutMedicineFragment;
import com.health.darooyar.feature.tracker.presentation.list.adapter.PrescriptionAdapter;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PrescriptionDetailFragment extends BaseFragment {
    private PrescriptionModel prescriptionModel;

    private int idDoctorName = 63515;
    private int idDate = 44754;
    private int idHovaShafi = 6541;
    private MedicineAdapter medicineAdapter;
    private ConstraintLayout emptyStateParent;

    public void setPrescriptionModel(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate() {
        appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);
        parent.setBackgroundColor(Color.getBackgroundColor());

        PrescriptionDetailEvent prescriptionDetailEvent = new PrescriptionDetailEvent(this);

        ConstraintLayout cardParent=new ConstraintLayout(activity);
        TextView tvDoctorName = new TextView(activity);
        tvDoctorName.setText("دکتر: " + prescriptionModel.getDoctorName());
        tvDoctorName.setId(idDoctorName);
        tvDoctorName.setTypeface(appTheme.getMediumTypeface(), Typeface.BOLD);
        parent.addView(tvDoctorName, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        TextView tvDate = new TextView(activity);
        tvDate.setText("تاریخ مراجعه: " + prescriptionModel.getDate());
        tvDate.setId(idDate);
        tvDate.setTypeface(appTheme.getMediumTypeface());
        parent.addView(tvDate, Param.consParam(-2, -2, -tvDoctorName.getId(), -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        TextView tvHovaShafi = new TextView(activity);
        tvHovaShafi.setText("او شفادهنده است");
        tvHovaShafi.setId(idHovaShafi);
        tvHovaShafi.setTextColor(Color.gray());
        tvHovaShafi.setTypeface(appTheme.getMediumTypeface());
        parent.addView(tvHovaShafi, Param.consParam(-2, -2, -tvDate.getId(), 0, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        RecyclerView rvMedicine = new RecyclerView(activity);
        rvMedicine.setVerticalFadingEdgeEnabled(true);
        rvMedicine.setFadingEdgeLength(150);
        rvMedicine.setOverScrollMode(View.OVER_SCROLL_NEVER);
        medicineAdapter = new MedicineAdapter(MedicineQueryImp.getInstance(activity).getMedicines(prescriptionModel.getId()), activity, prescriptionModel.getId(), itemChangedListeners);
        rvMedicine.setAdapter(medicineAdapter);
        rvMedicine.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parent.addView(rvMedicine, Param.consParam(0, 0, -tvHovaShafi.getId(), 0, 0, 0, Dimen.m12, -1, -1, Dimen.m40));

        ExtendedFloatingActionButton fabButton = new ExtendedFloatingActionButton(activity);
        fabButton.setText("افزودن دارو");
        fabButton.setTypeface(appTheme.getRegularTypeface());
        fabButton.setOnClickListener(prescriptionDetailEvent);
        fabButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        fabButton.setElevation(0);
        fabButton.setIcon(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_add, null));
        fabButton.setOnClickListener(v -> {
            PutMedicineFragment putMedicineFragment = new PutMedicineFragment();
            putMedicineFragment.setPrescriptionId(prescriptionModel.getId());
            putMedicineFragment.setListener(listener);
            activity.pushFragment(putMedicineFragment, null);
        });
        parent.addView(fabButton, Param.consParam(appTheme.getAf(400), appTheme.getAf(165), -1, -1, 0, 0, -1, -1, Dimen.m40, Dimen.m40));

        if (medicineAdapter.getItemCount() == 0) {
            loadEmptyState();
        } else
            hideEmptyState();

        return parent;
    }

    private PrescriptionAdapter.ItemChangedListeners itemChangedListeners = itemsCount -> {
        if (itemsCount == 0) {
            loadEmptyState();
        } else
            hideEmptyState();
    };

    private void loadEmptyState() {
        if (emptyStateParent == null) {
            emptyStateParent = new ConstraintLayout(activity);
            parent.addView(emptyStateParent, Param.consParam(0, -2, -idHovaShafi, 0, 0, 0, -1, Dimen.m64, Dimen.m64, Dimen.m40));

            ImageView imgState = new ImageView(activity);
            imgState.setImageResource(R.drawable.empty_state_medicine);
            imgState.setId(5684);
            emptyStateParent.addView(imgState, Param.consParam(-2, -2, 0, 0, 0, -1));

            TextView tvState = new TextView(activity);
            tvState.setText("می\u200Cتوانید یک دارو اضافه کنید");
            tvState.setTypeface(appTheme.getMediumTypeface());
            tvState.setTextColor(Color.gray());
            tvState.setTextSize(0, Dimen.fontSize14);
            emptyStateParent.addView(tvState, Param.consParam(-2, -2, -imgState.getId(), imgState.getId(), imgState.getId(), -1, -appTheme.getAf(350), -1, -1, -1));
        }
        emptyStateParent.setVisibility(View.VISIBLE);
    }

    private void hideEmptyState() {
        if (emptyStateParent != null)
            emptyStateParent.setVisibility(View.GONE);
    }

    private PutMedicineFragment.AddDataListener listener = (model) -> {
        medicineAdapter.addItem(model);
    };

    @Override
    protected void onHideChange(boolean isHide) {
        super.onHideChange(isHide);
        parent.setBackgroundColor(Color.getBackgroundColor());
    }
}

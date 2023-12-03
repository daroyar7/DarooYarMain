package com.health.darooyar.feature.tracker.presentation.list;

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
import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionModel;
import com.health.darooyar.feature.tracker.data.database.prescription.PrescriptionQueryImp;
import com.health.darooyar.feature.tracker.presentation.list.adapter.PrescriptionAdapter;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.Text;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class PrescriptionFragment extends BaseFragment {

    private RecyclerView recyclerView;
    public PrescriptionAdapter adapter;
    private ConstraintLayout emptyStateParent;

    @Override
    protected ViewGroup onViewFragmentCreate() {
        appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);
        parent.setBackgroundColor(Color.getBackgroundColor());

        PrescriptionEvent prescriptionEvent = new PrescriptionEvent(this);

        ExtendedFloatingActionButton fabButton = new ExtendedFloatingActionButton(activity);
        fabButton.setText(Text.ADD_PRESCRIPTION);
        fabButton.setTypeface(appTheme.getRegularTypeface());
        fabButton.setOnClickListener(prescriptionEvent);
        fabButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        fabButton.setIcon(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_add, null));
        parent.addView(fabButton, Param.consParam(appTheme.getAf(400), appTheme.getAf(165), -1, -1, 0, 0, -1, -1, Dimen.m40, Dimen.m40));

        recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter = new PrescriptionAdapter(PrescriptionQueryImp.getInstance(activity).getPrescriptions(), activity,listener);
        recyclerView.setAdapter(adapter);
        parent.addView(recyclerView, Param.consParam(0, 0, 0, 0, 0, 0));

        if (adapter.getItemCount() == 0) {
            loadEmptyState();
        } else
            hideEmptyState();

        return parent;
    }
    private PrescriptionAdapter.ItemChangedListeners listener= itemsCount -> {
        if (itemsCount == 0) {
            loadEmptyState();
        } else
            hideEmptyState();
    };

    private void loadEmptyState() {
        if (emptyStateParent == null) {
            emptyStateParent = new ConstraintLayout(activity);
            parent.addView(emptyStateParent, Param.consParam(0, -2, 0, 0, 0, 0, -1, Dimen.m40, Dimen.m40, -1));

            ImageView imgState = new ImageView(activity);
            imgState.setImageResource(R.drawable.empty_state_prescription);
            imgState.setId(5684);
            emptyStateParent.addView(imgState, Param.consParam(-2, -2, 0, 0, 0, -1));

            TextView tvState = new TextView(activity);
            tvState.setText("می\u200Cتوانید یک نسخه اضافه کنید");
            tvState.setTypeface(appTheme.getMediumTypeface());
            tvState.setTextColor(Color.getOnBackgroundColor());
            tvState.setTextSize(0, Dimen.fontSize14);
            emptyStateParent.addView(tvState, Param.consParam(-2, -2, -imgState.getId(), imgState.getId(), imgState.getId(), -1, -appTheme.getAf(280), -1, -1, -1));
        }
        emptyStateParent.setVisibility(View.VISIBLE);
    }

    private void hideEmptyState() {
        if (emptyStateParent != null)
            emptyStateParent.setVisibility(View.GONE);
    }

    @Override
    protected void onHideChange(boolean isHide) {
        if (!isHide) {
            recyclerView.setAdapter(new PrescriptionAdapter(PrescriptionQueryImp.getInstance(activity).getPrescriptions(), activity,listener));
            appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);
            if (recyclerView.getAdapter().getItemCount() == 0) {
                loadEmptyState();
            } else
                hideEmptyState();
        }
    }

}

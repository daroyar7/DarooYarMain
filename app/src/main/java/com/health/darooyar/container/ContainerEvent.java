package com.health.darooyar.container;

import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.health.darooyar.feature.alert.presentation.AlertFragment;
import com.health.darooyar.feature.tracker.presentation.list.PrescriptionFragment;
import com.google.android.material.navigation.NavigationBarView;

public class ContainerEvent implements NavigationBarView.OnItemSelectedListener {

    private int itemSelected = 1;

    private PrescriptionFragment prescriptionFragment;
    private AlertFragment alertFragment;
    private FragmentManager fragmentManager;

    public ContainerEvent(ContainerActivity containerActivity) {
        prescriptionFragment = new PrescriptionFragment();

        fragmentManager = containerActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ContainerActivity.CONTAINER_ID, prescriptionFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getOrder() == itemSelected) {
            return false;
        }
        itemSelected = item.getOrder();
        switch (itemSelected) {
            case 0:
                selectAlert();
                break;
            case 1:
                selectPrescription();
        }
        return true;
    }

    private void selectPrescription() {
        prescriptionFragment.showFragment(fragmentManager, true);
        if (alertFragment != null)
            alertFragment.hideFragment(fragmentManager, true);
    }

    private void selectAlert() {
        prescriptionFragment.hideFragment(fragmentManager, true);
        if (alertFragment == null) {
            alertFragment = new AlertFragment();
            fragmentManager.beginTransaction().add(ContainerActivity.CONTAINER_ID, alertFragment).commit();
        } else
            alertFragment.showFragment(fragmentManager, true);
    }
}

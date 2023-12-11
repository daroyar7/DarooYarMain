package com.health.darooyar.container;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;

public abstract class BaseFragment extends Fragment {

    public AppTheme appTheme;
    public ContainerActivity activity;
    public ConstraintLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (ContainerActivity) getActivity();
        appTheme = AppTheme.getInstance();

        if (activity == null)
            return null;
        parent = new ConstraintLayout(activity);
        parent.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        parent.setBackgroundColor(Color.getBackgroundColor());
        parent.setMotionEventSplittingEnabled(false);
        parent.setFocusableInTouchMode(true);
        parent.setFocusable(true);
        parent.setOnClickListener(null);
        parent.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        return onViewFragmentCreate();
    }

    public View getParentView(){
        return parent;
    }

    public void hideFragment(FragmentManager manager, boolean isNavigation) {
        onHideChange(true);
        if (parent != null)
            parent.setVisibility(View.GONE);
        if (isNavigation)
            manager.beginTransaction().hide(this).commit();
    }

    public void showFragment(FragmentManager manager, boolean isNavigation) {
        if (parent != null)
            parent.setVisibility(View.VISIBLE);
        onHideChange(false);
        if (isNavigation)
            manager.beginTransaction().show(this).commit();
    }

    public void onPermissionResult(int resultCode, String[] permissions, int[] grantResults) {
    }

    public void onActivityResult(Intent intent, int requestCode, int resultCode) {
    }

    public void onAdDismissedFullScreenContent(){

    }

    protected void onHideChange(boolean isHide) {

    }

    public void onConnectionChanged(boolean isAvailable) {

    }

    public void reloadText() {

    }

    public void reloadColor() {

    }

    public void reloadFeatures() {

    }

    public void emitMessageListener(String emitName, Object... data) {

    }

    protected abstract ViewGroup onViewFragmentCreate();

    public void onBackPressed() {
    }
}

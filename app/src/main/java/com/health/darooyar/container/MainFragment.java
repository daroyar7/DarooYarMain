package com.health.darooyar.container;

import android.content.res.ColorStateList;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.health.darooyar.R;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;

public class MainFragment extends BaseFragment{

    @Override
    protected ViewGroup onViewFragmentCreate() {

        ContainerEvent containerEvent = new ContainerEvent(activity);
        AppTheme appTheme = AppTheme.getInstance();


        appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);

        int[][] states = new int[][]{new int[]{android.R.attr.state_checked}, new int[]{-android.R.attr.state_checked}};
        int[] colors = new int[]{Color.getOnBackgroundColor(), Color.getOnBackgroundColor()};
        ColorStateList stateList = new ColorStateList(states, colors);
        BottomNavigationView bottomNavigationView = new BottomNavigationView(activity);
        bottomNavigationView.setId(ContainerActivity.BOTTOM_NAVIGATION_ID);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setBackgroundColor(Color.getBackgroundColor());
        bottomNavigationView.setItemTextColor(stateList);
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_prescription);
        bottomNavigationView.setOnItemSelectedListener(containerEvent);
        parent.addView(bottomNavigationView, Param.consParam(0, appTheme.getAf(200), -1, 0, 0, 0));

        FrameLayout containerView = new FrameLayout(activity);
        containerView.setId(ContainerActivity.CONTAINER_ID);
        parent.addView(containerView, Param.consParam(0, 0, 0, 0, 0, -ContainerActivity.BOTTOM_NAVIGATION_ID));


        return parent;
    }

}

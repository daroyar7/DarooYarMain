package com.example.darooyar2.container;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.darooyar2.R;
import com.example.darooyar2.them.AppTheme;
import com.example.darooyar2.them.Color;
import com.example.darooyar2.them.Param;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ContainerActivity extends AppCompatActivity {

    protected final static int PARENT_ID = 27;
    protected final static int BOTTOM_NAVIGATION_ID = 278;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout parent = new ConstraintLayout(this);
        ContainerEvent containerEvent = new ContainerEvent(this);
        AppTheme appTheme = AppTheme.getInstance();

        setContentView(parent);
        appTheme.setUpStatusBar(this, Color.getBackgroundColor(), true);

        int[][] states = new int[][]{new int[]{android.R.attr.state_checked}, new int[]{-android.R.attr.state_checked}};
        int[] colors = new int[]{Color.getOnBackgroundColor(), Color.getSurfaceColor()};
        ColorStateList stateList = new ColorStateList(states, colors);
        BottomNavigationView bottomNavigationView = new BottomNavigationView(this);
        bottomNavigationView.setId(BOTTOM_NAVIGATION_ID);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setBackgroundColor(Color.getBackgroundColor());
        bottomNavigationView.setItemTextColor(stateList);
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_prescription);
        bottomNavigationView.setOnItemSelectedListener(containerEvent);
        parent.addView(bottomNavigationView, Param.consParam(0, appTheme.getAf(200), -1, 0, 0, 0));

        FrameLayout containerView = new FrameLayout(this);
        containerView.setId(PARENT_ID);
        parent.addView(containerView, Param.consParam(0, 0, 0, 0, 0, -BOTTOM_NAVIGATION_ID));
    }
}
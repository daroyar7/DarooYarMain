package com.health.darooyar.container;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.health.darooyar.R;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ContainerActivity extends AppCompatActivity {

    protected final static int CONTAINER_ID = 27;
    protected final static int PARENT_ID = 81;
    protected final static int BOTTOM_NAVIGATION_ID = 278;

    public ArrayList<BaseFragment> fragmentStack = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout parent = new ConstraintLayout(this);
        parent.setId(PARENT_ID);
        setContentView(parent, new ViewGroup.LayoutParams(-1, -1));

      pushFragment(new MainFragment(), null);
    }

    public void pushFragment(BaseFragment fragment, String tag) {
        pushFragment(fragment, tag, R.anim.scale_open_transition, R.anim.scale_exit_transition, 500);
    }

    public void pushFragment(BaseFragment fragment, String tag, int openAnimation, int exitAnimation) {
        pushFragment(fragment, tag, openAnimation, exitAnimation, 500);
    }

    public void pushFragment(BaseFragment fragment, String tag, int openAnimation, int exitAnimation, int delayHide) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().setCustomAnimations(openAnimation, exitAnimation)
                .setReorderingAllowed(true).add(PARENT_ID, fragment, tag).commit();
        fragmentStack.add(fragment);

        AppLoader.handler.postDelayed(() -> {
            if (fragmentStack.size() > 1)
                fragmentStack.get(fragmentStack.size() - 2).hideFragment(fragmentManager, false);
        }, delayHide);
    }

    public void popFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setReorderingAllowed(true).remove(fragment).commit();
        fragmentStack.remove(fragmentStack.size() - 2);
    }

    public void popFragment(boolean isBack) {
        popFragment(isBack, R.anim.scale_open_transition, R.anim.scale_exit_transition);
    }

    public void popFragment(boolean isBack, int openAnimation, int exitAnimation) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().setCustomAnimations(openAnimation, exitAnimation)
                    .setReorderingAllowed(true).remove(getCurrentFragment()).commit();
            if (isBack)
                fragmentStack.get(fragmentStack.size() - 2).showFragment(fragmentManager, false);
            fragmentStack.remove(fragmentStack.size() - 1);
        } catch (Exception ignored) {
        }
    }

    public BaseFragment getCurrentFragment() {
        if (fragmentStack.size() == 0)
            return null;
        return fragmentStack.get(fragmentStack.size() - 1);
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            popFragment(true);
        } else {
            super.onBackPressed();
        }
    }

}
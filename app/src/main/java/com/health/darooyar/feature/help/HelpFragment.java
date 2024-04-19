package com.health.darooyar.feature.help;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;

public class HelpFragment extends BaseFragment {


    @Override
    protected ViewGroup onViewFragmentCreate() {
        appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);
        parent.setBackgroundColor(Color.getBackgroundColor());

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setBackgroundColor(Color.getErrorColor());
        parent.addView(linearLayout, Param.consParam(-1, -1));

        return parent;
    }

    @Override
    protected void onHideChange(boolean isHide) {
        super.onHideChange(isHide);
        parent.setBackgroundColor(Color.getBackgroundColor());
    }
}

package com.health.darooyar.theme.component;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.health.darooyar.R;
import com.health.darooyar.container.ContainerActivity;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;

public class Toolbar extends ConstraintLayout {

    private ImageView imgBack;
    private TextView txtTitle;


    public Toolbar(Context context, String title, int titleColor, int backgroundColor, int hoverColor) {
        super(context);

        setBackgroundColor(backgroundColor);

        txtTitle = new TextView(context);
        txtTitle.setText(title);
        txtTitle.setTextColor(titleColor);
        txtTitle.setTypeface(AppTheme.getInstance().getMediumTypeface());
        txtTitle.setTextSize(0, Dimen.fontSize20);
        addView(txtTitle, Param.consParam(-2, -2, 0, 0, 0, 0));

        imgBack = new ImageView(context);
        imgBack.setImageResource(R.drawable.ic_back);
        imgBack.setOnClickListener((view) -> ((ContainerActivity) context).popFragment(true));
        imgBack.setBackground(AppTheme.getInstance().createRoundSelectorDrawable(hoverColor, Color.parseColor("#00FFFFFF"), AppTheme.getInstance().getAf(60), AppTheme.getInstance().getAf(60)));
        imgBack.setPadding(AppTheme.getInstance().getAf(20), AppTheme.getInstance().getAf(20), AppTheme.getInstance().getAf(20), AppTheme.getInstance().getAf(20));
        addView(imgBack, Param.consParam(AppTheme.getInstance().getAf(110), AppTheme.getInstance().getAf(110), 0, 0, -1, 0, AppTheme.getInstance().getAf(35), AppTheme.getInstance().getAf(35), -1, AppTheme.getInstance().getAf(35)));
    }

    public void changeImgBackListener(OnClickListener onClickListener) {
        imgBack.setOnClickListener(onClickListener);
    }

    public void changeTitle(String title) {
        txtTitle.setText(title);
    }

    public void hideImgBack() {
        imgBack.setVisibility(View.INVISIBLE);
    }
}

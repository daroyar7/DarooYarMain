package com.health.darooyar.feature.help;

import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.health.darooyar.R;
import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;

public class HelpFragment extends BaseFragment {


    @Override
    protected ViewGroup onViewFragmentCreate() {
        appTheme.setUpStatusBar(activity, Color.getBackgroundColor(), false);
        parent.setBackgroundColor(android.graphics.Color.WHITE);

        ScrollView scrollView = new ScrollView(activity);
        parent.addView(scrollView, Param.consParam(-1, -2));

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        scrollView.addView(linearLayout, Param.frameParam(-1, -2));

        View vt1 = createTextContent("سلام رفیق\n\nبه اپلیکیشن دارویار خوش آمدی\n\nامیداورم تجربه ی خوبی رو با اپلیکیشن داشته باشی\n\nاپلیکیشن دارویار جهت یاداوری و ثبت نسخه ی دارو می باشد ");
        linearLayout.addView(vt1, Param.linearParam(-1, -2, appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vi1 = createImageContent(R.drawable.ic_one);
        linearLayout.addView(vi1, Param.linearParam(appTheme.getAf(537), appTheme.getAf(1102), appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vt2 = createTextContent("این  صفحه ی اصلی برنامه هست که برای ثبت نسخه ی خود ابتدا می بایست روی دکمه افزودن کلیک کرده و نسخه خود را وارد کنید\nضمنن با کلیک روی قسمت یادآور پس از ثبت نسخه های خود زمان مصرف دارو ها را ببینید");
        linearLayout.addView(vt2, Param.linearParam(-1, -2, appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vi2 = createImageContent(R.drawable.ic_two);
        linearLayout.addView(vi2, Param.linearParam(appTheme.getAf(537), appTheme.getAf(1102), appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vt3 = createTextContent("در این قسمت از اپلیکیشن می توانید  نام نسخه ، نام پزشک و تاریخ مراجعه را ثبت نمایید که وارد کردن آن ها هیج الزامی ندارند ولی ثبت این اطلاعات در اپلیکیشن به شما کمک خواهد کرد و پس از ثبت اطلاعات روی گزینه ی ثبت نسخه کلیک کنید");
        linearLayout.addView(vt3, Param.linearParam(-1, -2, appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vi3 = createImageContent(R.drawable.ic_three);
        linearLayout.addView(vi3, Param.linearParam(appTheme.getAf(537), appTheme.getAf(1102), appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vt4 = createTextContent("حال پس از ثبت شدن نسخه ی شما مانند تصوير بالا ، شما میتوانید با کلیک بر روی آیکون سطل زباله آن را پاک کنید و با کلیک بر روی آیکون ویرایش نسخه را تغییر دهید و با کلیک بر روی نسخه میتوانید اقدام به ثبت دارو های خود کنید ضمنن با کلیک بر روی افزودن نیز میتوانید نسخه ی های دیگری اضافه کنید");
        linearLayout.addView(vt4, Param.linearParam(-1, -2, appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vi4 = createImageContent(R.drawable.ic_four);
        linearLayout.addView(vi4, Param.linearParam(appTheme.getAf(537), appTheme.getAf(1102), appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vt5 = createTextContent("شما در این صفحه به نسخه ی دکتر وارد شدید و میتوانید با کلیک بر روی گزینه ی افزودن داروی خود را ثبت نمایید");
        linearLayout.addView(vt5, Param.linearParam(-1, -2, appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vi5 = createImageContent(R.drawable.ic_five);
        linearLayout.addView(vi5, Param.linearParam(appTheme.getAf(537), appTheme.getAf(1102), appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        View vt6 = createTextContent("در این قسمت از اپلیکیشن میتوانید نام دارو تاریخ شروع دارو و زمان شروع آن را ثبت کنید ضمنن میتوانید دوره مصرف آن را نیز ثبت کنید تا اپلیکیشن زمان مصرف دارو را به شما یاد آوری کند در قسمت توضیحات شما میتوانید صدا و یا عکس که مربوط به دارو می باشد ثبت کنید تا در زمان مصرف به شما یادآوری شوند");
        linearLayout.addView(vt6, Param.linearParam(-1, -2, appTheme.getAf(50), appTheme.getAf(50), appTheme.getAf(50), 0));

        return parent;
    }

    private View createTextContent(String text) {
        TextView tvHovaShafi = new TextView(activity);
        tvHovaShafi.setText(text);
        tvHovaShafi.setTextColor(android.graphics.Color.parseColor("#000000"));
        tvHovaShafi.setTypeface(appTheme.getMediumTypeface());
        return tvHovaShafi;
    }

    private View createImageContent(int image) {
        ImageView tvHovaShafi = new ImageView(activity);
        tvHovaShafi.setImageResource(image);
        tvHovaShafi.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return tvHovaShafi;
    }

    @Override
    protected void onHideChange(boolean isHide) {
        super.onHideChange(isHide);
        parent.setBackgroundColor(Color.getBackgroundColor());
    }
}

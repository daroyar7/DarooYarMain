package com.example.darooyar2.feature.tracker.presentation.detail.Medicine.add;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import com.example.darooyar2.R;
import com.example.darooyar2.container.BaseFragment;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.example.darooyar2.theme.component.FormFieldView;
import com.example.darooyar2.theme.component.NumberPicker.DurationPicker;
import com.google.android.material.button.MaterialButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddMedicineFragment extends BaseFragment {
    private int idBtnSubmit = 5455;
    private int idFormNameView = 5455;
    private int idFormDurationView = 548;

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getBackgroundColor());
        AppTheme.getInstance().setUpStatusBar(activity, Color.getBackgroundColor(), false);

        AddMedicineEvent event = new AddMedicineEvent(this);

//        ImageView imgMedicine = new ImageView(activity);
//        imgMedicine.setImageResource(R.drawable.img_medicine);
//        imgMedicine.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imgMedicine.setId(68);
//        parent.addView(imgMedicine, Param.consParam(-1,(int) (0.4*appTheme.heightPixels) , 0, 0, 0, -1));

        FormFieldView formNameView = new FormFieldView(activity);
        formNameView.setId(idFormNameView);
        formNameView.setUp(R.drawable.ic_medicine, "نام دارو:");
        parent.addView(formNameView, Param.consParam(-1, -2, 0, 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        FormFieldView formDetailView = new FormFieldView(activity);
        formDetailView.setUp(R.drawable.ic_info, "نام دارو:");
        formDetailView.setId(idFormDurationView);
        parent.addView(formDetailView, Param.consParam(-1, -2, -formNameView.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        TextView tvDuration = new TextView(activity);
        tvDuration.setText("دوره ی مصرف:");
        tvDuration.setId(6895);
        parent.addView(tvDuration, Param.consParam(-2, -2, -formNameView.getId(), -1, 0, -1));

        DurationPicker durationPicker = new DurationPicker(activity);
        durationPicker.setId(58845);
        durationPicker.setListener(new DurationPicker.OnDurationChangedListener() {
            @Override
            public void onDurationChanged(int duration) {

            }
        });
        parent.addView(durationPicker, Param.consParam(-2, -2, -tvDuration.getId(), -1, 0, -1));

        MaterialSpinner spinner =new MaterialSpinner(activity);
        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
        parent.addView(spinner,Param.consParam(appTheme.getAf(150),-2,durationPicker.getId(),0,-durationPicker.getId(),durationPicker.getId()));

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setOnClickListener(event);
        btnSubmit.setText("ثبت نسخه");
        btnSubmit.setId(idBtnSubmit);
        parent.addView(btnSubmit, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        return parent;
    }
}

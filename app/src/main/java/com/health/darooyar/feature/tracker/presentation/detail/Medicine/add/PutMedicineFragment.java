package com.health.darooyar.feature.tracker.presentation.detail.Medicine.add;

import static android.app.Activity.RESULT_OK;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.darooyar.R;
import com.health.darooyar.container.AppLoader;
import com.health.darooyar.container.BaseFragment;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Dimen;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.component.DatePickerView;
import com.health.darooyar.theme.component.FormFieldView;
import com.health.darooyar.theme.component.NumberPicker.DurationPicker;
import com.health.darooyar.theme.component.TimePickerView;
import com.health.darooyar.theme.component.Toolbar;
import com.health.darooyar.theme.component.VoiceDescriptionView;
import com.google.android.material.button.MaterialButton;
import com.health.darooyar.theme.component.cropper.CropImage;
import com.health.darooyar.theme.component.cropper.CropImageView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class PutMedicineFragment extends BaseFragment {
    protected int idBtnSubmit = 5455;
    protected int idFormNameView = 8544;
    protected int idFormDetailView = 554;
    protected int idFieldDate = 8084;
    protected int idFieldTime = 541;
    protected int idSpinner = 4587;
    protected int idDurationPicker = 58845;
    private MedicineModel medicineModel;
    private long prescriptionId;
    private AddDataListener listener;
    private boolean isRecorder = true;
    private String txtDefaultDate = "", txtDefaultTime = "";
    private String base64Image = "";

    private ImageView ivMedicinePicture;


    public void setListener(AddDataListener listener) {
        this.listener = listener;
    }

    public void setReadOnly(String txtDefaultDate, String txtDefaultTime) {
        this.txtDefaultDate = txtDefaultDate;
        this.txtDefaultTime = txtDefaultTime;
        this.isRecorder = false;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setDefaultModel(MedicineModel medicineModel) {
        this.medicineModel = medicineModel;
        Log.i("Sarina", "onViewFragmentCreate: " + (medicineModel == null ? "null" : medicineModel.getId()));
        base64Image = medicineModel.getMedicineImage();
    }

    @Override
    protected ViewGroup onViewFragmentCreate() {
        parent.setBackgroundColor(Color.getBackgroundColor());
        AppTheme.getInstance().setUpStatusBar(activity, Color.getBackgroundColor(), false);

        appTheme.setUpStatusBar(activity, Color.toolbarBackground(), true);
        Toolbar toolbar = new Toolbar(activity, isRecorder ? (medicineModel == null ? "داروی جدید" : "ویرایش") : "نمایش", Color.getOnSecondaryColor(), Color.toolbarBackground(), android.graphics.Color.parseColor("#383838"));
        toolbar.setId(8457);
        parent.addView(toolbar, Param.consParam(0, -2, 0, 0, 0, -1));

        Log.i("Sarina", "onViewFragmentCreate: " + (medicineModel == null ? "null" : medicineModel.getId()));
        PutMedicineEvent event = new PutMedicineEvent(this, medicineModel, prescriptionId);

        FormFieldView formNameView = new FormFieldView(activity);
        formNameView.setId(idFormNameView);
        formNameView.setUp(R.drawable.ic_medicine, "نام دارو", medicineModel == null ? "" : medicineModel.getName());
        parent.addView(formNameView, Param.consParam(-1, -2, -toolbar.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        DatePickerView fieldDate = new DatePickerView(activity);
        fieldDate.setId(idFieldDate);
        fieldDate.setUp(R.drawable.ic_date, "تاریخ شروع", isRecorder ? (medicineModel == null ? "" : medicineModel.getStartDate()) : txtDefaultDate);
        parent.addView(fieldDate, Param.consParam(0, -2, -formNameView.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        TimePickerView fieldTime = new TimePickerView(activity);
        fieldTime.setId(idFieldTime);
        fieldTime.setUp(R.drawable.ic_time, "زمان شروع", isRecorder ? (medicineModel == null ? "" : medicineModel.getStartTime()) : txtDefaultTime);
        parent.addView(fieldTime, Param.consParam(0, -2, -fieldDate.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        // //////////////////////////////////////////////////////
        ImageView icDuration = new ImageView(activity);
        icDuration.setId(65965);
        icDuration.setImageResource(R.drawable.ic_duration);
        icDuration.setColorFilter(Color.getOnBackgroundColor());
        parent.addView(icDuration, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), -fieldTime.getId(), -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        TextView tvDuration = new TextView(activity);
        tvDuration.setText("دوره ی مصرف:");
        tvDuration.setTypeface(appTheme.getRegularTypeface());
        tvDuration.setId(6895);
        parent.addView(tvDuration, Param.consParam(-2, -2, icDuration.getId(), -1, -icDuration.getId(), icDuration.getId(), -1, -1, Dimen.m16, -1));

        MaterialSpinner spinner = new MaterialSpinner(activity);
        spinner.setId(idSpinner);
        spinner.setTypeface(appTheme.getRegularTypeface());
        spinner.setBackground(appTheme.createRoundDrawable(appTheme.getAf(100), Color.getOnPrimaryColor()));
        spinner.setItems(PutMedicineEvent.durationUnits);
        spinner.setGravity(Gravity.CENTER);
        spinner.setSelectedIndex(medicineModel == null ? 0 : (PutMedicineEvent.durationUnits.indexOf(medicineModel.getDurationUnit())));
        spinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            event.setDurationUnit(item);
        });
        parent.addView(spinner, Param.consParam(appTheme.getAf(250), -2, tvDuration.getId(), 0, -1, tvDuration.getId(), -1, Dimen.m40, -1, -1));

        DurationPicker durationPicker = new DurationPicker(activity, medicineModel == null ? 0 : medicineModel.getDurationNumber());
        durationPicker.setId(idDurationPicker);
        durationPicker.setListener(event::setDuration);
        parent.addView(durationPicker, Param.consParam(-2, appTheme.getAf(250), -tvDuration.getId(), -spinner.getId(), -tvDuration.getId(), tvDuration.getId()));

        final int VOICE_ID = 48941;
        VoiceDescriptionView voiceDescriptionView = new VoiceDescriptionView(activity, isRecorder);
        voiceDescriptionView.setId(VOICE_ID);
        voiceDescriptionView.setUp(R.drawable.ic_info, "توضیحات:", medicineModel == null ? "v-" + prescriptionId + "-" + System.currentTimeMillis() : medicineModel.getDetail());
        voiceDescriptionView.setId(idFormDetailView);
        parent.addView(voiceDescriptionView, Param.consParam(-1, -2, -durationPicker.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        ImageView ivCameraIcon = new ImageView(activity);
        ivCameraIcon.setId(6488);
        ivCameraIcon.setOnClickListener(v -> startCamera());
        ivCameraIcon.setImageResource(R.drawable.ic_camera);
        if (isRecorder)
            parent.addView(ivCameraIcon, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), -icDuration.getId(), -1, icDuration.getId(), -1, appTheme.getAf(500), 0, 0, 0));
        else {
            ivCameraIcon.setVisibility(View.INVISIBLE);
            parent.addView(ivCameraIcon, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), -icDuration.getId(), -1, icDuration.getId(), -1, appTheme.getAf(640), 0, 0, 0));
        }


        ivMedicinePicture = new ImageView(activity);
        if (isRecorder)
            ivMedicinePicture.setOnClickListener(v -> startCamera());
        if (medicineModel != null) {
            if (TextUtils.isEmpty(medicineModel.getMedicineImage()))
                ivMedicinePicture.setImageResource(R.drawable.image_default);
            else {
                byte[] decodedString = Base64.decode(medicineModel.getMedicineImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivMedicinePicture.setImageBitmap(decodedByte);
            }
        } else
            ivMedicinePicture.setImageResource(R.drawable.image_default);
        parent.addView(ivMedicinePicture, Param.consParam(appTheme.getAf(240), appTheme.getAf(240), ivCameraIcon.getId(), 0, -icDuration.getId(), ivCameraIcon.getId(), 0, appTheme.getAf(50), 0, 0));

        MaterialButton btnSubmit = new MaterialButton(activity);
        btnSubmit.setTypeface(appTheme.getMediumTypeface());
        btnSubmit.setOnClickListener(event);
        btnSubmit.setTextSize(0, appTheme.getAf(42));
        btnSubmit.setText("ثبت دارو");
        btnSubmit.setId(idBtnSubmit);
        btnSubmit.setCornerRadius(appTheme.getAf(25));
        parent.addView(btnSubmit, Param.consParam(-1, appTheme.getAf(158), -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        if (!isRecorder) {
            setReadOnly();
            CheckBox checkBoxHsTaken = new CheckBox(activity);
            checkBoxHsTaken.setTypeface(appTheme.getMediumTypeface());
            if (medicineModel.getTimeMustUsed() + 5 * 60 * 1000 > System.currentTimeMillis()) {
                checkBoxHsTaken.setChecked(false);
                checkBoxHsTaken.setEnabled(false);
            } else {
                if (AppLoader.sharedPreferences.getBoolean(medicineModel.getTimeMustUsed() + medicineModel.hashCode() + "", false)) {
                    checkBoxHsTaken.setChecked(true);
                    checkBoxHsTaken.setEnabled(false);
                } else {
                    checkBoxHsTaken.setChecked(false);
                    checkBoxHsTaken.setEnabled(true);
                    checkBoxHsTaken.setOnCheckedChangeListener((compoundButton, b) -> {
                        if (b) {
                            checkBoxHsTaken.setEnabled(false);
                            if (listener != null)
                                listener.onAddData(medicineModel);
                        }
                    });
                }
            }
            checkBoxHsTaken.setText("قرصم را خوردم");
            parent.addView(checkBoxHsTaken, Param.consParam(-2, -2, medicineModel.getDetail().equals("") ? -idDurationPicker : -idFormDetailView,
                    -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));
        }
        return parent;
    }

    private void startCamera() {
        CropImage.activity(CropImage.CROP_IMAGE_SOURCE_CAMERA)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setBorderLineThickness(3)
                .setGuidelinesThickness(3)
                .setBorderCornerThickness(5)
                .setBorderCornerLength(50)
                .setBorderCornerOffset(0)
                .setFixAspectRatio(true)
                .setBorderCornerColor(Color.getPrimaryColor())
                .setGuidelinesColor(Color.gray())
                .start(activity);
    }

    @Override
    public void onActivityResult(Intent data, int requestCode, int resultCode) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                final String pathFile = getPath(activity, resultUri);
                base64Image = decodeFile(pathFile);
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivMedicinePicture.setImageBitmap(decodedByte);
            }
        }
    }

    public String getBase64Image() {
        return base64Image;
    }

    public String getDescriptionLink() {
        return ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).getFileName();
    }

    public void itemAdded(MedicineModel model) {
        listener.onAddData(model);
    }

    public interface AddDataListener {
        void onAddData(MedicineModel model);
    }

    private void setReadOnly() {
        parent.findViewById(idFormNameView).setEnabled(false);
        parent.findViewById(idFieldDate).setEnabled(false);
        parent.findViewById(idFieldTime).setEnabled(false);
        parent.findViewById(idSpinner).setEnabled(false);
        ((DurationPicker) parent.findViewById(idDurationPicker)).disable();
        parent.findViewById(idBtnSubmit).setVisibility(View.GONE);
        if (medicineModel.getDetail().equals(""))
            parent.findViewById(idFormDetailView).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((VoiceDescriptionView) parent.findViewById(idFormDetailView)).onDestroyView();
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }

    public static String decodeFile(String filePatch) {
        try {
            InputStream inputStream = new FileInputStream(filePatch);
            byte[] buffer = new byte[8192];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int byteImage;
            while ((byteImage = inputStream.read(buffer)) != -1)
                byteArrayOutputStream.write(buffer, 0, byteImage);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (Exception ignored) {
            return "";
        }
    }

    public static String getPath(Context context, Uri uri) {
        return FilePath.getPath(context, uri);
    }

    private static class FilePath {

        public static String getPath(final Context context, final Uri uri) {

            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.parseLong(id));

                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            } else if (uri != null && uri.getScheme() != null && "content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            } else if (uri != null && uri.getScheme() != null && "file".equalsIgnoreCase(uri.getScheme()))
                return uri.getPath();

            return null;
        }

        private static String getDataColumn(Context context, Uri uri,
                                            String selection, String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        private static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        private static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }
    }
}

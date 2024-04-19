package com.health.darooyar.theme.component.cropper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.health.darooyar.R;
import com.health.darooyar.theme.AppTheme;
import com.health.darooyar.theme.Color;
import com.health.darooyar.theme.Param;
import com.health.darooyar.theme.component.ColoredButton;
import com.health.darooyar.theme.component.TransparentButton;

import java.io.File;
import java.io.IOException;

public class CropImageActivity extends Activity implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

    private ColoredButton btnConfirm;
    private ImageView imgCancel;
    private TransparentButton btnRotate;

    /**
     * The crop image view library widget used in the activity
     */
    private CropImageView cropImageView;

    private boolean clickable = true;

    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private String imageSource;

    /**
     * the options that were set for the crop image
     */
    private CropImageOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppTheme appTheme = AppTheme.getInstance();
        appTheme.setUpStatusBar(this, Color.cropImageBackground(), true);
        setContentView(R.layout.crop_image_activity);

        cropImageView = findViewById(R.id.cropImageView);
        cropImageView.setBackgroundColor(Color.cropImageBackground());

        ConstraintLayout toolsParent = findViewById(R.id.tools);
        toolsParent.setBackground(AppTheme.createRoundDrawable(appTheme.getAf(100), appTheme.getAf(100), Color.bottomSheetBackground()));

        btnRotate = new TransparentButton(this);
        btnRotate.setId(783783);
        btnRotate.setup("چرخش", Color.cropImageText(), appTheme.getAf(32), appTheme.getMediumTypeface(), " ", R.drawable.ic_rotate_crop, true, onClickListener);
        toolsParent.addView(btnRotate, Param.consParam(-2, -2, 0, 0, 0, 0, -1, -1, -1, -1));

        imgCancel = new ImageView(this);
        imgCancel.setId(24837);
        imgCancel.setOnClickListener(onClickListener);
        imgCancel.setImageResource(R.drawable.ic_close);
        imgCancel.setPadding(appTheme.getAf(30), appTheme.getAf(30), appTheme.getAf(30), appTheme.getAf(30));
        imgCancel.setBackground(AppTheme.createRoundDrawable(appTheme.getAf(100), appTheme.getAf(100), Color.edtBackground()));
        toolsParent.addView(imgCancel, Param.consParam(-2, -2, 0, 0, -1, 0, appTheme.getAf(40), appTheme.getAf(40), -1, appTheme.getAf(40)));

        btnConfirm = new ColoredButton(this);
        btnConfirm.setId(198);
        btnConfirm.setup("تایید", Color.btnLoginEnabledText(), Color.btnLoginHover(), Color.getPrimaryColor(), appTheme.getAf(38), onClickListener);
        btnConfirm.changeRadius(appTheme.getAf(100));
        btnConfirm.changePadding(appTheme.getAf(50), appTheme.getAf(20), appTheme.getAf(50), appTheme.getAf(20));
        toolsParent.addView(btnConfirm, Param.consParam(-2, -2, 0, -1, 0, 0, appTheme.getAf(40), -1, appTheme.getAf(40), appTheme.getAf(40)));

        Bundle bundle = getIntent().getBundleExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE);
        imageSource = getIntent().getStringExtra(CropImage.CROP_IMAGE_SOURCE);
        options = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);

        if (savedInstanceState == null) {
            if (imageSource.equals(CropImage.CROP_IMAGE_SOURCE_CAMERA)) {
                CropImage.startCameraIntent(this);

            } else if (imageSource.equals(CropImage.CROP_IMAGE_SOURCE_GALLERY)) {
                CropImage.startPickImageActivity(this);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        cropImageView.setOnSetImageUriCompleteListener(this);
        cropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cropImageView.setOnSetImageUriCompleteListener(null);
        cropImageView.setOnCropImageCompleteListener(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setResultCancel();
            } else if (resultCode == Activity.RESULT_OK) {
                Uri uri = CropImage.getPickImageResultUri(this, data);
                if (imageSource.equals(CropImage.CROP_IMAGE_SOURCE_CAMERA)) {
                    File f = new File(CropImage.currentPhotoPath);
                    Uri contentUri = Uri.fromFile(f);
                    cropImageView.setImageUriAsync(contentUri);
                } else {
                    cropImageView.setImageUriAsync(uri);
                }
            }
        }
    }

    protected void rotateImage(int degrees) {
        cropImageView.rotateImage(degrees);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            if (options.initialCropWindowRectangle != null) {
                cropImageView.setCropRect(options.initialCropWindowRectangle);
            }
        } else {
            setResult(null, error, 1);
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        setResult(result.getUri(), result.getError(), result.getSampleSize());
    }

    /**
     * Execute crop image and save the result tou output uri.
     */
    private void cropImage() {
        if (options.noOutputImage) {
            setResult(null, null, 1);
        } else {
            Uri outputUri = getOutputUri();
            cropImageView.saveCroppedImageAsync(
                    outputUri,
                    options.outputCompressFormat,
                    options.outputCompressQuality,
                    options.outputRequestWidth,
                    options.outputRequestHeight,
                    options.outputRequestSizeOptions);
        }
    }

    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        Uri outputUri = options.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                String ext =
                        options.outputCompressFormat == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : options.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
            } catch (IOException e) {
            }
        }
        return outputUri;
    }

    /**
     * Result with cropped image data or error if failed.
     */
    protected void setResult(Uri uri, Exception error, int sampleSize) {
        int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
        setResult(resultCode, getResultIntent(uri, error, sampleSize));
        finish();
    }

    /**
     * Cancel of cropping activity.
     */
    protected void setResultCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * Get intent instance to be used for the result of this activity.
     */
    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        CropImage.ActivityResult result =
                new CropImage.ActivityResult(
                        cropImageView.getImageUri(),
                        uri,
                        error,
                        cropImageView.getCropPoints(),
                        cropImageView.getCropRect(),
                        cropImageView.getRotatedDegrees(),
                        cropImageView.getWholeImageRect(),
                        sampleSize);
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        return intent;
    }

    private final View.OnClickListener onClickListener = (view) -> {
        if (!clickable)
            return;

        if (view.getId() == btnConfirm.getId()) {
            cropImage();
        } else if (view.getId() == imgCancel.getId()) {
            setResultCancel();
        } else if (view.getId() == btnRotate.getId()) {
            rotateImage(-options.rotationDegrees);
        }
    };

}

package com.health.darooyar.theme.component.cropper;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class CropImage {

    /**
     * The key used to pass crop image source URI to {@link CropImageActivity}.
     */
    public static final String CROP_IMAGE_EXTRA_SOURCE = "CROP_IMAGE_EXTRA_SOURCE";

    /**
     * The key used to pass crop image options to {@link CropImageActivity}.
     */
    public static final String CROP_IMAGE_EXTRA_OPTIONS = "CROP_IMAGE_EXTRA_OPTIONS";

    /**
     * The key used to pass crop image bundle data to {@link CropImageActivity}.
     */
    public static final String CROP_IMAGE_EXTRA_BUNDLE = "CROP_IMAGE_EXTRA_BUNDLE";

    /**
     * The key used to pass crop image result data back from {@link CropImageActivity}.
     */
    public static final String CROP_IMAGE_EXTRA_RESULT = "CROP_IMAGE_EXTRA_RESULT";

    /**
     * The request code used to start pick image activity to be used on result to identify the this
     * specific request.
     */
    public static final int PICK_IMAGE_CHOOSER_REQUEST_CODE = 200;

    /**
     * The request code used to request permission to pick image from external storage.
     */
    public static final int PICK_IMAGE_PERMISSIONS_REQUEST_CODE = 201;

    /**
     * The request code used to request permission to capture image from camera.
     */
    public static final int CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE = 2011;

    /**
     * The request code used to start {@link CropImageActivity} to be used on result to identify the
     * this specific request.
     */
    public static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203;

    /**
     * The result code used to return error from {@link CropImageActivity}.
     */
    public static final int CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE = 204;

    public static final String CROP_IMAGE_SOURCE_CAMERA = "camera";
    public static final String CROP_IMAGE_SOURCE_GALLERY = "gallery";
    public static final String CROP_IMAGE_SOURCE = "source";

    private CropImage() {
    }

    public static Bitmap toOvalBitmap(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        int color = 0xff424242;
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        RectF rect = new RectF(0, 0, width, height);
        canvas.drawOval(rect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        bitmap.recycle();

        return output;
    }

    public static void startPickImageActivity(@NonNull Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PICK_IMAGE_CHOOSER_REQUEST_CODE);
    }

    public static String currentPhotoPath;

    public static Intent startCameraIntent(@NonNull Activity context) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                // Create an image file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );

                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = image.getAbsolutePath();

                photoFile = image;
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.health.darooyar.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                context.startActivityForResult(takePictureIntent, PICK_IMAGE_CHOOSER_REQUEST_CODE);
            }
        }

        return takePictureIntent;
    }


    public static Uri getCaptureImageOutputUri(@NonNull Context context) {
        File getImage = Environment.getExternalStorageDirectory();
        File file = new File(getImage.getPath(), "pickImageResult.jpeg");
        Uri imageUri = FileProvider.getUriForFile(
                context,
                "com.health.darooyar.provider", //(use your app signature + ".provider" )
                file);

        return imageUri;
    }

    public static List<Intent> getCameraIntents(
            @NonNull Context context, @NonNull PackageManager packageManager) {

        List<Intent> allIntents = new ArrayList<>();

        // Determine Uri of camera image to  save.
        Uri outputFileUri = getCaptureImageOutputUri(context);

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        return allIntents;
    }

    public static List<Intent> getGalleryIntents(
            @NonNull PackageManager packageManager, String action, boolean includeDocuments) {
        List<Intent> intents = new ArrayList<>();
        Intent galleryIntent =
                action == Intent.ACTION_GET_CONTENT
                        ? new Intent(action)
                        : new Intent(action, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            intents.add(intent);
        }

        // remove documents intent
        if (!includeDocuments) {
            for (Intent intent : intents) {
                if (intent
                        .getComponent()
                        .getClassName()
                        .equals("com.android.documentsui.DocumentsActivity")) {
                    intents.remove(intent);
                    break;
                }
            }
        }
        return intents;
    }

    public static boolean hasPermissionInManifest(
            @NonNull Context context, @NonNull String permissionName) {
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equalsIgnoreCase(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }


    public static Uri getPickImageResultUri(@NonNull Context context, @Nullable Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera || data.getData() == null ? getCaptureImageOutputUri(context) : data.getData();
    }

    public static boolean isReadExternalStoragePermissionsRequired(Context context) {
        String readImagePermission = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            readImagePermission = Manifest.permission.READ_MEDIA_IMAGES;
        else
            readImagePermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(readImagePermission) != PackageManager.PERMISSION_GRANTED;
        }

        return false;
    }


    public static ActivityBuilder activity() {
        return new ActivityBuilder(null);
    }

    public static ActivityBuilder activity(@Nullable String uri) {
        return new ActivityBuilder(uri);
    }

    public static ActivityResult getActivityResult(@Nullable Intent data) {
        return data != null ? (ActivityResult) data.getParcelableExtra(CROP_IMAGE_EXTRA_RESULT) : null;
    }

    public static final class ActivityBuilder {

        @Nullable
        private final String mSource;

        private final CropImageOptions mOptions;

        private ActivityBuilder(@Nullable String source) {
            mSource = source;
            mOptions = new CropImageOptions();
        }

        public Intent getIntent(@NonNull Context context) {
            return getIntent(context, CropImageActivity.class);
        }

        public Intent getIntent(@NonNull Context context, @Nullable Class<?> cls) {
            mOptions.validate();

            Intent intent = new Intent();
            intent.setClass(context, cls);
            Bundle bundle = new Bundle();
            bundle.putParcelable(CROP_IMAGE_EXTRA_OPTIONS, mOptions);
            intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle);
            intent.putExtra(CropImage.CROP_IMAGE_SOURCE, mSource);
            return intent;
        }

        public void start(@NonNull Activity activity) {
            mOptions.validate();
            activity.startActivityForResult(getIntent(activity), CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        public void start(@NonNull Activity activity, @Nullable Class<?> cls) {
            mOptions.validate();
            activity.startActivityForResult(getIntent(activity, cls), CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        public void start(@NonNull Context context, @NonNull Fragment fragment) {
            fragment.startActivityForResult(getIntent(context), CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        public void start(@NonNull Context context, @NonNull android.app.Fragment fragment) {
            fragment.startActivityForResult(getIntent(context), CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        public void start(
                @NonNull Context context, @NonNull Fragment fragment, @Nullable Class<?> cls) {
            fragment.startActivityForResult(getIntent(context, cls), CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        public void start(
                @NonNull Context context, @NonNull android.app.Fragment fragment, @Nullable Class<?> cls) {
            fragment.startActivityForResult(getIntent(context, cls), CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        public ActivityBuilder setCropShape(@NonNull CropImageView.CropShape cropShape) {
            mOptions.cropShape = cropShape;
            return this;
        }

        public ActivityBuilder setSnapRadius(float snapRadius) {
            mOptions.snapRadius = snapRadius;
            return this;
        }

        public ActivityBuilder setTouchRadius(float touchRadius) {
            mOptions.touchRadius = touchRadius;
            return this;
        }

        public ActivityBuilder setGuidelines(@NonNull CropImageView.Guidelines guidelines) {
            mOptions.guidelines = guidelines;
            return this;
        }

        public ActivityBuilder setScaleType(@NonNull CropImageView.ScaleType scaleType) {
            mOptions.scaleType = scaleType;
            return this;
        }

        public ActivityBuilder setShowCropOverlay(boolean showCropOverlay) {
            mOptions.showCropOverlay = showCropOverlay;
            return this;
        }

        public ActivityBuilder setAutoZoomEnabled(boolean autoZoomEnabled) {
            mOptions.autoZoomEnabled = autoZoomEnabled;
            return this;
        }

        public ActivityBuilder setMultiTouchEnabled(boolean multiTouchEnabled) {
            mOptions.multiTouchEnabled = multiTouchEnabled;
            return this;
        }

        public ActivityBuilder setMaxZoom(int maxZoom) {
            mOptions.maxZoom = maxZoom;
            return this;
        }

        public ActivityBuilder setInitialCropWindowPaddingRatio(float initialCropWindowPaddingRatio) {
            mOptions.initialCropWindowPaddingRatio = initialCropWindowPaddingRatio;
            return this;
        }

        public ActivityBuilder setFixAspectRatio(boolean fixAspectRatio) {
            mOptions.fixAspectRatio = fixAspectRatio;
            return this;
        }

        public ActivityBuilder setAspectRatio(int aspectRatioX, int aspectRatioY) {
            mOptions.aspectRatioX = aspectRatioX;
            mOptions.aspectRatioY = aspectRatioY;
            mOptions.fixAspectRatio = true;
            return this;
        }

        public ActivityBuilder setBorderLineThickness(float borderLineThickness) {
            mOptions.borderLineThickness = borderLineThickness;
            return this;
        }

        public ActivityBuilder setBorderLineColor(int borderLineColor) {
            mOptions.borderLineColor = borderLineColor;
            return this;
        }

        public ActivityBuilder setBorderCornerThickness(float borderCornerThickness) {
            mOptions.borderCornerThickness = borderCornerThickness;
            return this;
        }

        public ActivityBuilder setBorderCornerOffset(float borderCornerOffset) {
            mOptions.borderCornerOffset = borderCornerOffset;
            return this;
        }

        public ActivityBuilder setBorderCornerLength(float borderCornerLength) {
            mOptions.borderCornerLength = borderCornerLength;
            return this;
        }

        public ActivityBuilder setBorderCornerColor(int borderCornerColor) {
            mOptions.borderCornerColor = borderCornerColor;
            return this;
        }

        public ActivityBuilder setGuidelinesThickness(float guidelinesThickness) {
            mOptions.guidelinesThickness = guidelinesThickness;
            return this;
        }

        public ActivityBuilder setGuidelinesColor(int guidelinesColor) {
            mOptions.guidelinesColor = guidelinesColor;
            return this;
        }

        public ActivityBuilder setBackgroundColor(int backgroundColor) {
            mOptions.backgroundColor = backgroundColor;
            return this;
        }

        public ActivityBuilder setMinCropWindowSize(int minCropWindowWidth, int minCropWindowHeight) {
            mOptions.minCropWindowWidth = minCropWindowWidth;
            mOptions.minCropWindowHeight = minCropWindowHeight;
            return this;
        }

        public ActivityBuilder setMinCropResultSize(int minCropResultWidth, int minCropResultHeight) {
            mOptions.minCropResultWidth = minCropResultWidth;
            mOptions.minCropResultHeight = minCropResultHeight;
            return this;
        }

        public ActivityBuilder setMaxCropResultSize(int maxCropResultWidth, int maxCropResultHeight) {
            mOptions.maxCropResultWidth = maxCropResultWidth;
            mOptions.maxCropResultHeight = maxCropResultHeight;
            return this;
        }

        public ActivityBuilder setActivityTitle(CharSequence activityTitle) {
            mOptions.activityTitle = activityTitle;
            return this;
        }


        public ActivityBuilder setOutputUri(Uri outputUri) {
            mOptions.outputUri = outputUri;
            return this;
        }

        public ActivityBuilder setOutputCompressFormat(Bitmap.CompressFormat outputCompressFormat) {
            mOptions.outputCompressFormat = outputCompressFormat;
            return this;
        }

        public ActivityBuilder setOutputCompressQuality(int outputCompressQuality) {
            mOptions.outputCompressQuality = outputCompressQuality;
            return this;
        }

        public ActivityBuilder setRequestedSize(int reqWidth, int reqHeight) {
            return setRequestedSize(reqWidth, reqHeight, CropImageView.RequestSizeOptions.RESIZE_INSIDE);
        }

        public ActivityBuilder setRequestedSize(
                int reqWidth, int reqHeight, CropImageView.RequestSizeOptions options) {
            mOptions.outputRequestWidth = reqWidth;
            mOptions.outputRequestHeight = reqHeight;
            mOptions.outputRequestSizeOptions = options;
            return this;
        }

        public ActivityBuilder setNoOutputImage(boolean noOutputImage) {
            mOptions.noOutputImage = noOutputImage;
            return this;
        }

        public ActivityBuilder setInitialCropWindowRectangle(Rect initialCropWindowRectangle) {
            mOptions.initialCropWindowRectangle = initialCropWindowRectangle;
            return this;
        }

        public ActivityBuilder setInitialRotation(int initialRotation) {
            mOptions.initialRotation = (initialRotation + 360) % 360;
            return this;
        }

        public ActivityBuilder setAllowRotation(boolean allowRotation) {
            mOptions.allowRotation = allowRotation;
            return this;
        }

        public ActivityBuilder setAllowFlipping(boolean allowFlipping) {
            mOptions.allowFlipping = allowFlipping;
            return this;
        }

        public ActivityBuilder setAllowCounterRotation(boolean allowCounterRotation) {
            mOptions.allowCounterRotation = allowCounterRotation;
            return this;
        }

        public ActivityBuilder setRotationDegrees(int rotationDegrees) {
            mOptions.rotationDegrees = (rotationDegrees + 360) % 360;
            return this;
        }

        public ActivityBuilder setFlipHorizontally(boolean flipHorizontally) {
            mOptions.flipHorizontally = flipHorizontally;
            return this;
        }

        public ActivityBuilder setFlipVertically(boolean flipVertically) {
            mOptions.flipVertically = flipVertically;
            return this;
        }

        public ActivityBuilder setCropMenuCropButtonTitle(CharSequence title) {
            mOptions.cropMenuCropButtonTitle = title;
            return this;
        }

        public ActivityBuilder setCropMenuCropButtonIcon(@DrawableRes int drawableResource) {
            mOptions.cropMenuCropButtonIcon = drawableResource;
            return this;
        }
    }

    public static final class ActivityResult extends CropImageView.CropResult implements Parcelable {

        public static final Creator<ActivityResult> CREATOR =
                new Creator<ActivityResult>() {
                    @Override
                    public ActivityResult createFromParcel(Parcel in) {
                        return new ActivityResult(in);
                    }

                    @Override
                    public ActivityResult[] newArray(int size) {
                        return new ActivityResult[size];
                    }
                };

        public ActivityResult(
                Uri originalUri,
                Uri uri,
                Exception error,
                float[] cropPoints,
                Rect cropRect,
                int rotation,
                Rect wholeImageRect,
                int sampleSize) {
            super(
                    null,
                    originalUri,
                    null,
                    uri,
                    error,
                    cropPoints,
                    cropRect,
                    wholeImageRect,
                    rotation,
                    sampleSize);
        }

        protected ActivityResult(Parcel in) {
            super(
                    null,
                    (Uri) in.readParcelable(Uri.class.getClassLoader()),
                    null,
                    (Uri) in.readParcelable(Uri.class.getClassLoader()),
                    (Exception) in.readSerializable(),
                    in.createFloatArray(),
                    (Rect) in.readParcelable(Rect.class.getClassLoader()),
                    (Rect) in.readParcelable(Rect.class.getClassLoader()),
                    in.readInt(),
                    in.readInt());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(getOriginalUri(), flags);
            dest.writeParcelable(getUri(), flags);
            dest.writeSerializable(getError());
            dest.writeFloatArray(getCropPoints());
            dest.writeParcelable(getCropRect(), flags);
            dest.writeParcelable(getWholeImageRect(), flags);
            dest.writeInt(getRotation());
            dest.writeInt(getSampleSize());
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

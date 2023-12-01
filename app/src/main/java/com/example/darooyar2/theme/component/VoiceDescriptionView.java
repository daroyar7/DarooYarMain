package com.example.darooyar2.theme.component;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.darooyar2.R;
import com.example.darooyar2.container.AppLoader;
import com.example.darooyar2.container.ContainerActivity;
import com.example.darooyar2.theme.AppTheme;
import com.example.darooyar2.theme.Color;
import com.example.darooyar2.theme.Dimen;
import com.example.darooyar2.theme.Param;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VoiceDescriptionView extends ConstraintLayout {
    private ContainerActivity activity;
    private AppTheme appTheme = AppTheme.getInstance();
    public static final int idIcon = 8485;
    public static final int idBtnRecord = 8488;
    public static final int idTvTimer = 9556;
    private ImageView btnRecord;
    private TextView tvTitle, tvTimer;
    private boolean isRecording;
    private Timer timer;
    private int counterSec = 0;
    private int counterMin = 0;
    private MediaRecorder recorder;
    private String location;
    private Boolean isRecorder = true;
    private ExoPlayer player;
    private MediaPlayer mediaPlayer;
    private String fileName;
    public static final int PERMISSION_RECORDING = 14585;

    public VoiceDescriptionView(@NonNull Context context, boolean isRecorder) {
        super(context);
        activity = (ContainerActivity) context;
        this.isRecorder = isRecorder;
    }

    public void setUp(int icon, String text, String fileName) {
        this.fileName = fileName;
        location = getFilePath(fileName);

        ImageView icField = new ImageView(activity);
        icField.setId(idIcon);
        icField.setImageResource(icon);
        icField.setColorFilter(Color.getOnBackgroundColor());
        addView(icField, Param.consParam(appTheme.getAf(120), appTheme.getAf(120), 0, -1, 0, 0));

        tvTitle = new TextView(activity);
        tvTitle.setText(text);
        tvTitle.setId(695);
        addView(tvTitle, Param.consParam(-2, -2, icField.getId(), -1, -icField.getId(), icField.getId(), -1, -1, Dimen.m16, -1));

        btnRecord = new ImageView(activity);
        btnRecord.setId(idBtnRecord);
        btnRecord.setOnClickListener(onClickListener);
        btnRecord.setTag("microphone");
        btnRecord.setImageResource(isRecorder ? R.drawable.ic_microphone : R.drawable.ic_play_state);
        btnRecord.setColorFilter(Color.getPrimaryColor());
        btnRecord.setBackground(AppTheme.createRoundDrawable(appTheme.getAf(200), appTheme.getAf(200), Color.getOnSecondaryColor()));
        btnRecord.setPadding(Dimen.m24, Dimen.m24, Dimen.m24, Dimen.m24);
        addView(btnRecord, Param.consParam(appTheme.getAf(210), appTheme.getAf(210), tvTitle.getId(), -1, -tvTitle.getId(), tvTitle.getId(), -1, -1, Dimen.m16, -1));

        tvTimer = new TextView(activity);
        tvTimer.setId(idTvTimer);
        tvTimer.setText("00:00");
        tvTimer.setTextColor(Color.getOnBackgroundColor());
        addView(tvTimer, Param.consParam(-2, -2, btnRecord.getId(), -1, -btnRecord.getId(), btnRecord.getId(), -1, -1, Dimen.m16, -1));

        Log.i("Sarina", "setUp: " + location);
        if (!isRecorder) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(location);
                mediaPlayer.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


//            player = new ExoPlayer.Builder(activity).build();
//            MediaItem mediaItem = MediaItem.fromUri( location);
//            player.setMediaItem(mediaItem);
//            player.prepare();
//            player.addListener(new Player.Listener() {
//                @Override
//                public void onPlayerError(PlaybackException error) {
//                    Log.i("Sarian", "onPlayerError: "+error.getMessage());
//                }
//            });
        }
    }

    private OnClickListener onClickListener = v -> {
        if (isRecorder) {
            if (!isRecording) {
                if (PackageManager.PERMISSION_GRANTED !=
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)) {
                    activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORDING + 2);
                } else
                    startRecording();
            } else {
                stopRecording();
            }
        } else {
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying()) {
                    btnRecord.setImageResource(R.drawable.ic_pause_state);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
//                    player.seekTo(0);
//                    player.play();
                } else {
                    btnRecord.setImageResource(R.drawable.ic_play_state);
                    mediaPlayer.pause();
                }
            }

        }
    };


    private void startRecording() {
        isRecording = true;
        counterMin = 0;
        counterSec = 0;
        tvTimer.setText("00:00");
        tvTimer.setTextColor(Color.getErrorColor());
        btnRecord.setColorFilter(Color.getOnSecondaryColor());
        btnRecord.setBackground(AppTheme.createRoundDrawable(appTheme.getAf(200), appTheme.getAf(200), Color.getSecondaryColor()));


        setupRecorder();

        timer = new Timer();
        //limit=2 mins
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AppLoader.handler.post(() -> {
                    counterSec++;
                    if (counterSec >= 60) {
                        counterSec = 0;
                        counterMin++;
                        if (counterMin >= 2) {
                            tvTimer.setText(String.format("%02d", counterMin) + ":" + String.format("%02d", counterSec));
                            stopRecording();
                        }
                    }
                    tvTimer.setText(String.format("%02d", counterMin) + ":" + String.format("%02d", counterSec));
                });
            }
        }, 1000, 1000);

    }

    private void setupRecorder() {
        try {
            Log.i("Sarina", "setupRecorder: " + location);
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(location);
            recorder.prepare();
            recorder.start();
        } catch (Exception ignored) {
            Log.i("Sarina", "setupRecorder Exception: " + ignored);
        }
    }

    private void stopRecording() {
        isRecording = false;

        tvTimer.setTextColor(Color.getOnBackgroundColor());
        btnRecord.setColorFilter(Color.getPrimaryColor());
        btnRecord.setBackground(AppTheme.createRoundDrawable(appTheme.getAf(200), appTheme.getAf(200), Color.getOnSecondaryColor()));

        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }

        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
            } catch (IllegalStateException e) {
                Log.i("Sarina", "stopRecording: " + e.getMessage());
            }

        }

    }

    private String getFilePath(String name) {
        String directoryPath = activity.getCacheDir().getAbsolutePath() + "/descriptions/";

        File directory = new File(directoryPath);
        directory.mkdirs();

        return directoryPath + name.trim() + ".mp3";
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileLocation() {
        if (!tvTimer.getText().equals("00:00"))
            return location;
        return "";
    }

    public void onDestroyView() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }

        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
            } catch (IllegalStateException e) {
                Log.i("Sarina", "stopRecording: " + e.getMessage());
            }

        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}

package com.health.darooyar.feature.alarm;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.health.darooyar.R;
import com.health.darooyar.container.ContainerActivity;
import com.health.darooyar.theme.Color;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager manager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG : Notification");
        wakeLock.acquire(30);

        createNotification(context);

        AlarmSetter.startingAlarm(context);

        wakeLock.release();
    }

    public static void createNotification(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            notificationManager.createNotificationChannel(channel);
        }
        builder = new NotificationCompat.Builder(context, "default");

        Intent intent = new Intent(context, ContainerActivity.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= 31)
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        else
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("وقت خوردن دارو رسیده")
                .setContentIntent(pendingIntent)
                .setContentText("لطفا سریعا داروی خود را مصرف کنید")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(Color.getPrimaryColor())
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            return;
        notificationManager.notify(new Random().nextInt(), builder.build());
    }
}

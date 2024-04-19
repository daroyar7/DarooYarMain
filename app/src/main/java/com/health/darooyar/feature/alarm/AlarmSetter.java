package com.health.darooyar.feature.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.health.darooyar.feature.tracker.data.database.medicine.MedicineModel;
import com.health.darooyar.feature.tracker.data.database.medicine.MedicineQueryImp;

import java.util.ArrayList;

public class AlarmSetter {

    public static void startingAlarm(Context context) {

        ArrayList<MedicineModel> medicineModels = MedicineQueryImp.getInstance(context).getMedicinesToday();
        Log.i("TAG", "setAlarm2: " + medicineModels.size());
        MedicineModel chosenMedicine = null;
        long nearest = Long.MAX_VALUE;
        for (int i = 0; i < medicineModels.size(); i++) {
            Log.i("TAG", "startingAlarm nearestTaking: " + medicineModels.get(i).nearestTaking());
            Log.i("TAG", "startingAlarm nearest: " + nearest);
            Log.i("TAG", "startingAlarm currentTimeMillis: " +  System.currentTimeMillis());
            if (medicineModels.get(i).nearestTaking() > System.currentTimeMillis() && medicineModels.get(i).nearestTaking() < nearest) {
                Log.i("TAG", "startingAlarm: chose");
                nearest = medicineModels.get(i).nearestTaking();
                chosenMedicine = medicineModels.get(i);
            }
            Log.i("TAG", "startingAlarm: ---------------------------------");
        }
        Log.i("TAG", "setAlarm3: "+(chosenMedicine != null));
        if (chosenMedicine != null) {
            AlarmSetter.removeAlarm(context, chosenMedicine);
            AlarmSetter.setAlarm(context, chosenMedicine);
        }
    }

    private static void setAlarm(Context activity, MedicineModel medicineModel) {
        ComponentName receiver = new ComponentName(activity, AlarmReceiver.class);
        PackageManager pm = activity.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(activity, AlarmReceiver.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= 31)
            pendingIntent = PendingIntent.getBroadcast(activity, medicineModel.getNotificationId(), i, PendingIntent.FLAG_IMMUTABLE);
        else
            pendingIntent = PendingIntent.getBroadcast(activity, medicineModel.getNotificationId(), i, 0);

        long timeTrigger = medicineModel.nearestTaking();
        long tenMin = 10 * 60 * 1000;
        Log.i("TAG", "setAlarm timeTrigger: " + timeTrigger);
        if (Build.VERSION.SDK_INT < 31)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeTrigger, pendingIntent);
        else {
            if (alarmManager.canScheduleExactAlarms())
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeTrigger, pendingIntent);
            else
                alarmManager.setWindow(AlarmManager.RTC_WAKEUP, timeTrigger, tenMin, pendingIntent);
        }
    }

    public static void removeAlarm(Context context, MedicineModel medicineModel) {
        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        int requestCode = medicineModel.getNotificationId();

        Intent intentStop = new Intent(context, AlarmReceiver.class);
        PendingIntent senderStop;
        if (Build.VERSION.SDK_INT >= 31)
            senderStop = PendingIntent.getBroadcast(context, requestCode, intentStop, PendingIntent.FLAG_IMMUTABLE);
        else
            senderStop = PendingIntent.getBroadcast(context, requestCode, intentStop, 0);

        AlarmManager alarmManagerStop = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        senderStop.cancel();
        alarmManagerStop.cancel(senderStop);

    }
}

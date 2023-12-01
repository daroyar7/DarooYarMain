package com.health.darooyar.feature.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmSetter.startingAlarm(context);
        }
    }
}
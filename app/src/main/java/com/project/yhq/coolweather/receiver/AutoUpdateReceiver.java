package com.project.yhq.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.project.yhq.coolweather.service.AutoUpdateService;

/**
 * Created by yhq on 2016/3/5.
 */
public class AutoUpdateReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}

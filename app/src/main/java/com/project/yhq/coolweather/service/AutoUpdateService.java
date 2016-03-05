package com.project.yhq.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.project.yhq.coolweather.receiver.AutoUpdateReceiver;
import com.project.yhq.coolweather.util.HttpCallbackListener;
import com.project.yhq.coolweather.util.HttpUtil;
import com.project.yhq.coolweather.util.Utility;

/**
 * Created by yhq on 2016/2/27.
 */
public class AutoUpdateService extends Service
{

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateWeather();
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long anHour = 3600 * 1000;
        long triggerTime = System.currentTimeMillis() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String cityId = prefs.getString("city_id", "");
        String address = "String address = https://api.heweather.com/x3/weather?cityid=" + cityId + "&key=" + Utility.MY_ID;
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
        {
            @Override
            public void onFinish(String response)
            {
                Utility.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e)
            {
                e.printStackTrace();
            }
        });

    }

}

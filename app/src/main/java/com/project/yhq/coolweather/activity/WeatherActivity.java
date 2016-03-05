package com.project.yhq.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.yhq.coolweather.R;
import com.project.yhq.coolweather.service.AutoUpdateService;
import com.project.yhq.coolweather.util.HttpCallbackListener;
import com.project.yhq.coolweather.util.HttpUtil;
import com.project.yhq.coolweather.util.Utility;

/**
 * Created by yhq on 2016/2/24.
 */
public class WeatherActivity extends Activity implements View.OnClickListener
{
    private LinearLayout weatherInfoLayout;

    private TextView cityNameText;

    private TextView publishText;

    private TextView weatherDespText;

    private TextView temp1Text;

    private TextView temp2Text;

    private TextView currentDateText;

    private Button switchCity;

    private Button refreshWeather;

    private String cityId = null;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        currentDateText = (TextView) findViewById(R.id.current_date);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        cityId = getIntent().getStringExtra("city_id");
        if(!TextUtils.isEmpty(cityId))
        {
            //有城市id就去查询天气
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeather(cityId);
        } else
        {
            //没有城市id就直接显示本地天气
            showWeather();
        }
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    private void queryWeather(String cityId)
    {
        String address = "https://api.heweather.com/x3/weather?cityid="+ cityId +"&key=" + Utility.MY_ID;
        queryWeatherFromServer(address);
    }

    private  void queryWeatherFromServer (final String address)
    {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
        {
            @Override
            public void onFinish(String response)
            {
                Utility.handleWeatherResponse(WeatherActivity.this, response);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        showWeather();
                    }
                });
            }

            @Override
            public void onError(Exception e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
     */
    private void showWeather()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name", ""));
        publishText.setText("更新时间:" + prefs.getString("publish_time", ""));
        currentDateText.setText(prefs.getString("current_date", ""));
        weatherDespText.setText(prefs.getString("weather_desp", ""));
        temp1Text.setText(prefs.getString("temp1", ""));
        temp2Text.setText(prefs.getString("temp2", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中...");
                if (cityId != null)
                {
                    queryWeather(cityId);
                }
                else
                {
                    publishText.setText("同步失败");
                }
                break;
            default:
                break;
        }
    }
}

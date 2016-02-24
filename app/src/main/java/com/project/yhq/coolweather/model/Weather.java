package com.project.yhq.coolweather.model;

import java.util.List;

/**
 * Created by yhq on 2016/2/21.
 */
public class Weather
{
    //空气质量指数
    private int aqi;
    //数据更新当地时间
    private String loc;
    private String city;
    //pm 2.5
    private int pm25;
    //城市代码
    private String id;
    //纬度
    private float lat;
    //经度
    private float lon;
    //7天天气
    private DailyForecast[] dailyForecast = new DailyForecast[7];
    private RealtimeForecast realtimeForecast;
    private Suggestion[] suggestions = new Suggestion[7];

    public int getAqi()
    {
        return aqi;
    }

    public void setAqi(int aqi)
    {
        this.aqi = aqi;
    }

    public String getLoc()
    {
        return loc;
    }

    public void setLoc(String loc)
    {
        this.loc = loc;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public int getPm25()
    {
        return pm25;
    }

    public void setPm25(int pm25)
    {
        this.pm25 = pm25;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public float getLat()
    {
        return lat;
    }

    public void setLat(float lat)
    {
        this.lat = lat;
    }

    public float getLon()
    {
        return lon;
    }

    public void setLon(float lon)
    {
        this.lon = lon;
    }

    public DailyForecast[] getDailyForecast()
    {
        return dailyForecast;
    }

    public void setDailyForecast(DailyForecast[] dailyForecast)
    {
        this.dailyForecast = dailyForecast;
    }

    public RealtimeForecast getRealtimeForecast()
    {
        return realtimeForecast;
    }

    public void setRealtimeForecast(RealtimeForecast realtimeForecast)
    {
        this.realtimeForecast = realtimeForecast;
    }

    public Suggestion[] getSuggestions()
    {
        return suggestions;
    }

    public void setSuggestions(Suggestion[] suggestions)
    {
        this.suggestions = suggestions;
    }
}

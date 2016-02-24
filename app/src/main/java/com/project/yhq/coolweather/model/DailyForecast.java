package com.project.yhq.coolweather.model;

/**
 * Created by yhq on 2016/2/21.
 */
public class DailyForecast
{
    private String date;
    //最高摄氏温度
    private int max;
    private int min;
    //白天天气代码
    private int codeDay;
    //白天天气描述
    private String txtDay;
    private int codeNight;
    private String txtNight;
    //日出时间
    private String sr;
    //日落时间
    private String ss;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public String getSr()
    {
        return sr;
    }

    public void setSr(String sr)
    {
        this.sr = sr;
    }

    public String getSs()
    {
        return ss;
    }

    public void setSs(String ss)
    {
        this.ss = ss;
    }

    public int getMin()
    {
        return min;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public int getCodeDay()
    {
        return codeDay;
    }

    public void setCodeDay(int codeDay)
    {
        this.codeDay = codeDay;
    }

    public String getTxtDay()
    {
        return txtDay;
    }

    public void setTxtDay(String txtDay)
    {
        this.txtDay = txtDay;
    }

    public int getCodeNight()
    {
        return codeNight;
    }

    public void setCodeNight(int codeNight)
    {
        this.codeNight = codeNight;
    }

    public String getTxtNight()
    {
        return txtNight;
    }

    public void setTxtNight(String txtNight)
    {
        this.txtNight = txtNight;
    }
}

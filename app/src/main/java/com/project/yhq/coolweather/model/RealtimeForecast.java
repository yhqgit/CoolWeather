package com.project.yhq.coolweather.model;

/**
 * Created by yhq on 2016/2/21.
 */
public class RealtimeForecast
{
    private int code;
    private String txt;
    private int tmp;
    private int hum;
    //风向
    private String dir;
    //风力等级
    private String sc;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getTxt()
    {
        return txt;
    }

    public void setTxt(String txt)
    {
        this.txt = txt;
    }

    public int getTmp()
    {
        return tmp;
    }

    public void setTmp(int tmp)
    {
        this.tmp = tmp;
    }

    public int getHum()
    {
        return hum;
    }

    public void setHum(int hum)
    {
        this.hum = hum;
    }

    public String getSc()
    {
        return sc;
    }

    public void setSc(String sc)
    {
        this.sc = sc;
    }

    public String getDir()
    {
        return dir;
    }

    public void setDir(String dir)
    {
        this.dir = dir;
    }
}

package com.project.yhq.coolweather.model;

/**
 * Created by yhq on 2016/2/22.
 */
public class Suggestion
{
    //指数名字
    private String name;
    //简介
    private String brf;
    //详情
    private String txt;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBrf()
    {
        return brf;
    }

    public void setBrf(String brf)
    {
        this.brf = brf;
    }

    public String getTxt()
    {
        return txt;
    }

    public void setTxt(String txt)
    {
        this.txt = txt;
    }
}

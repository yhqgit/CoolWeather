package com.project.yhq.coolweather.model;

/**
 * Created by yhq on 2016/2/21.
 */
public class Province
{
    private int id;

    private String provinceName;

    public Province () {}

    public Province (int id, String provinceName)
    {
        this.id = id;
        this.provinceName = provinceName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }


}

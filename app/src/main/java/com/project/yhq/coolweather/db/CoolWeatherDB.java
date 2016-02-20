package com.project.yhq.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.yhq.coolweather.model.City;
import com.project.yhq.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhq on 2016/2/21.
 */
public class CoolWeatherDB
{
    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private CoolWeatherDB(Context context)
    {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例
     */
    public synchronized static CoolWeatherDB getInstance(Context context)
    {
        if (coolWeatherDB == null)
        {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储到数据库
     */
    public void saveProvince(Province province)
    {
        if (province != null)
        {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_id", province.getId());
            db.insert("Province", null, values);
        }
    }

    /**
     * 数据库读取全国所有的省份信息
     */
    public List<Province> loadProvince()
    {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst())  //光标到第一行
        {
            do
            {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        if(cursor != null)
        {
            cursor.close();
        }
        return list;
    }

    /**
     * 将City实例存储到数据库
     */
    public void saveCity(City city)
    {
        ContentValues values = new ContentValues();
        values.put("id", city.getId());
        values.put("city_name", city.getCityName());
        values.put("city_code", city.getCityCode());
        values.put("province_id", city.getProvinceId());
        db.insert("City", null, values);
    }

    /**
     * 数据库读取全国所有的城市信息
     */
    public List<City> loadCity()
    {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, null, null, null, null, null);
        if(cursor.moveToFirst())
        {
            do
            {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        if(cursor!=null)
        {
            cursor.close();
        }
        return list;
    }

}

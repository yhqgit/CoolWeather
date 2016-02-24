package com.project.yhq.coolweather.util;

import android.text.TextUtils;

import com.project.yhq.coolweather.activity.ChooseAreaActivity;
import com.project.yhq.coolweather.db.CoolWeatherDB;
import com.project.yhq.coolweather.model.City;
import com.project.yhq.coolweather.model.Province;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yhq on 2016/2/22.
 */
public class Utility
{
    /**
     * 保存所有的省份信息
     */
    private static final String[] ALL_PROVINCE = {"直辖市","河北", "山西", "辽宁",
            "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南",
            "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "台湾", "内蒙古", "广西",
            "西藏", "宁夏", "新疆", "特别行政区"};

    public static void saveAllProvince(CoolWeatherDB coolWeatherDB)
    {
         for (String p : ALL_PROVINCE)
         {
             Province province = new Province();
             province.setProvinceName(p);
             coolWeatherDB.saveProvince(province);
         }
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse (CoolWeatherDB coolWeatherDB, String response)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject all = new JSONObject(response);
                JSONArray cityInfo = all.getJSONArray("city_info");
                for (int i=0; i< cityInfo.length(); i++)
                {
                    JSONObject object = cityInfo.getJSONObject(i);
                    Province province = ChooseAreaActivity.getSelectedProvince();
                    /**
                     * 如果有选中省份而且选中省份和数据中的省份相同，则匹配
                     */
                    if(province!=null && province.getProvinceName().equals(object.getString("prov")))
                    {
                        City city = new City();
                        city.setCityName(object.getString("city"));
                        city.setCityCode(object.getString("id"));
                        city.setProvinceId(province.getId());
                        coolWeatherDB.saveCity(city);
                    }
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }


}

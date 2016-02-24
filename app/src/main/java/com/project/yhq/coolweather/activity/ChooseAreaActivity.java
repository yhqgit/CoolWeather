package com.project.yhq.coolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yhq.coolweather.R;
import com.project.yhq.coolweather.db.CoolWeatherDB;
import com.project.yhq.coolweather.model.City;
import com.project.yhq.coolweather.model.Province;
import com.project.yhq.coolweather.util.HttpCallbackListener;
import com.project.yhq.coolweather.util.HttpUtil;
import com.project.yhq.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhq on 2016/2/22.
 */
public class ChooseAreaActivity extends Activity
{
    public static final String MY_ID = "47474fd60deb41278daec41ec3d207a6";
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<String>();
    //省列表
    private List<Province> provinceList;
    //城市列表
    private List<City> cityList;
    //选中的省份
    private static Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);

        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               if(currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }
                else if(currentLevel == LEVEL_CITY)
                {
                    selectedCity = cityList.get(position);
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces()
    {
        provinceList = coolWeatherDB.loadProvinces();
        if(provinceList.size()>0)
        {
            dataList.clear();
            for(Province province: provinceList)
            {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }
        else
        {
            Utility.saveAllProvince(coolWeatherDB);
            queryProvinces();
        }
    }

    private void queryCities()
    {
        cityList = coolWeatherDB.loadCities(selectedProvince.getId());
        if(cityList.size()>0)
        {
            int provinceId = selectedProvince.getId();
            dataList.clear();
            for(City city : cityList)
            {
                if(city.getProvinceId() == provinceId)
                    dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }
        else
        {
            queryCitiesFromServer();
        }
    }

    private void queryCitiesFromServer()
    {
        String address;
        address = "https://api.heweather.com/x3/citylist?search=allchina&key=" + MY_ID;
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
        {
            @Override
            public void onFinish(String response)
            {
                boolean result = false;
                result = Utility.handleCitiesResponse(coolWeatherDB,response);
                if (result)
                {
                    //通过 runOnUiThread()方法回到主线程处理逻辑
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            closeProgressDialog();
                            queryCities();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e)
            {
                //
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public static Province getSelectedProvince()
    {
        return selectedProvince;
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog()
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog()
    {
        if (progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }

    public void onBackPressed()
    {
        if (currentLevel == LEVEL_CITY)
            queryProvinces();
        else finish();
    }

}

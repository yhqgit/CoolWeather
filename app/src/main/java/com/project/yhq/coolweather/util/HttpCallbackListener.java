package com.project.yhq.coolweather.util;

/**
 * Created by yhq on 2016/2/21.
 */
public interface HttpCallbackListener
{
    void onFinish(String response);

    void onError(Exception e);
}

package com.yangll.bishe.happyweather.http;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.bean.WeatherJson;

/**
 * Created by Administrator on 2016/11/23.
 */

public class HttpPost {

    private String address;
    private Handler mHandler;
    private int isLocation;
    private String city;

    public static final int POST_LOGIC_ERROR = 1;
    public static final int POST_SUCCES = 0;

    private int i;

    public HttpPost(String address, int isLocation, String city, Handler mHandler) {
        this.address = address;
        this.mHandler = mHandler;
        this.isLocation = isLocation;
        this.city = city;
        this.i = 2;
    }

    public HttpPost(String address, String city, Handler mHandler) {
        this.address = address;
        this.mHandler = mHandler;
        this.city = city;
        this.i = 3;
    }

    public HttpPost(String address, Handler mHandler) {
        this.address = address;
        this.mHandler = mHandler;
        this.i = 1;
    }

    public void exe(){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("Tag", response);
                Message msg = new Message();

                switch (i){
                    case 2:
                        Gson gson = new Gson();
                        WeatherJson weatherJson = gson.fromJson(response, WeatherJson.class);
                        String status = weatherJson.getHeWeather5().get(0).getStatus();

                        if (status.equals("ok")){
                            msg.what = POST_SUCCES;
                            Bundle bundle = new Bundle();
                            bundle.putString("response", response);
                            bundle.putString("cityname", city);
                            msg.setData(bundle);
                            msg.arg1 = isLocation;
                        }else {
                            msg.what = POST_LOGIC_ERROR;
                            msg.obj = status;
                        }
                        break;
                    case 3:
                        Gson gson1 = new Gson();
                        WeatherJson weatherJson1 = gson1.fromJson(response, WeatherJson.class);
                        String status1 = weatherJson1.getHeWeather5().get(0).getStatus();
                        if (status1.equals("ok")){
                            msg.what = POST_SUCCES;
                            Bundle bundle = new Bundle();
                            bundle.putString("response", response);
                            bundle.putString("cityname", city);
                            msg.setData(bundle);
                        }else {
                            msg.what = POST_LOGIC_ERROR;
                            msg.obj = status1;
                        }
                        break;
                    default:
                        msg.what = POST_SUCCES;
                        msg.obj = response;
                        break;
                }
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                Message msg = new Message();
                msg.what = POST_LOGIC_ERROR;
                msg.obj = e.getMessage().toString();
                Log.e("Tag", e.getMessage().toString());
                mHandler.sendMessage(msg);
            }
        });
    }

    public void imgexe(){
        HttpUtil.getUrlImg(address, new HttpImgCallbackListener() {

            @Override
            public void onFinish(Bitmap bmp) {
                Message msg = new Message();
                msg.what = POST_SUCCES;
                msg.obj = bmp;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                Message msg = new Message();
                msg.what = POST_LOGIC_ERROR;
                msg.obj = e.getMessage().toString();
                Log.e("Tag", e.getMessage().toString());
                mHandler.sendMessage(msg);
            }
        });
    }
}

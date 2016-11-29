package com.yangll.bishe.happyweather.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/23.
 */

public class HttpPost {

    private String address;
    private Handler mHandler;

    public static final int POST_LOGIC_ERROR = 1;
    public static final int POST_SUCCES = 0;

    public HttpPost(String address, Handler mHandler) {
        this.address = address;
        this.mHandler = mHandler;
    }

    public void exe(){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("Tag", response);
                Message msg = new Message();
                msg.what = POST_SUCCES;
                msg.obj = response;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                Message msg = new Message();
                msg.what = POST_LOGIC_ERROR;
                msg.obj = e.getMessage().toString();
                mHandler.sendMessage(msg);
            }
        });
    }
}

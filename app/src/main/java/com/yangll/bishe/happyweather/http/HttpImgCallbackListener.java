package com.yangll.bishe.happyweather.http;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/3/16.
 */

public interface HttpImgCallbackListener {

    void onFinish(Bitmap bmp);

    void onError(Exception e);
}

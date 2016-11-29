package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.DailyForecast;
import com.yangll.bishe.happyweather.http.WeatherUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/11/22.
 */

public class MainGalleryHolder extends BaseViewHolder {

    @BindView(R.id.weather_icon)
    ImageView weatherIcon;

    @BindView(R.id.weather_tmp)
    TextView weatherTmp;

    @BindView(R.id.weather_data)
    TextView weatherData;

    @BindView(R.id.item_bg)
    RelativeLayout item_bg;

    public MainGalleryHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_maingallery, listener);
    }

    @Override
    public void bindData(Object o) {
        DailyForecast dailyForecast = (DailyForecast) o;
        weatherIcon.setBackgroundResource(WeatherUtil.getWeatherIcon(dailyForecast.getCond().getCode_d()));
        weatherTmp.setText(dailyForecast.getTmp().getMin() + "°" + " - " + dailyForecast.getTmp().getMax() + "°");

        weatherData.setText(WeatherUtil.month_day(dailyForecast.getDate()));

        //设置选中状态
        if (dailyForecast.isSeclect()){
            item_bg.setBackgroundColor(Color.parseColor("#7f0080ff"));
        }else {
            item_bg.setBackgroundColor(0);
        }
    }
}

package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.DailyForecast;

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

    public MainGalleryHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_maingallery, listener);
    }

    @Override
    public void bindData(Object o) {
        DailyForecast dailyForecast = (DailyForecast) o;
        weatherTmp.setText(dailyForecast.getDate());
    }
}

package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.http.WeatherUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ManagerCityHolder extends BaseViewHolder {

    @BindView(R.id.cityname)
    TextView cityname;

    @BindView(R.id.weather_icon)
    ImageView weatherIcon;

    @BindView(R.id.weather_tmp)
    TextView weatherTmp;

    @BindView(R.id.item_bg)
    RelativeLayout itemBg;

    public ManagerCityHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_managercity, listener);
    }

    @Override
    public void bindData(Object o) {

        ViewGroup.LayoutParams lp = itemBg.getLayoutParams();
        lp.height = (int) (200 + Math.random() * 250);

        Weather weather = (Weather) o;
        cityname.setText(weather.getBasic().getCity());
        weatherIcon.setBackgroundResource(WeatherUtil.getWeatherIcon(weather.getNow().getCond().getCode()));
        weatherTmp.setText(weather.getNow().getTmp()+"°");
    }
}

package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.DailyForecast;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public class ManagerCityAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Weather> weathers = new ArrayList<>();
    private WeatherDB weatherDB;

    public ManagerCityAdapter2(Context context){
        this.context = context;
        weatherDB = WeatherDB.getInstance(context);
    }

    public void bindDatas(List<Weather> list){
        weathers.clear();
        if (null != list){
            weathers.addAll(list);
        }
    }

    public Weather getItem(int position){
        return weathers.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManagerCityHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_managercity2, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ManagerCityHolder2 holder2 = (ManagerCityHolder2) holder;
        DailyForecast dailyForecast = weathers.get(position).getDaily_forecast().get(0);
        holder2.item_name.setText(weathers.get(position).getBasic().getCity());
        holder2.item_icon.setBackgroundResource(WeatherUtil.getWeatherIcon(dailyForecast.getCond().getCode_d()));
        holder2.item_tmp.setText(dailyForecast.getTmp().getMin() + "°- " + dailyForecast.getTmp().getMax() + "°");

        if (weatherDB.getCityResponse(weathers.get(position).getBasic().getCity()).get(0).getIsLocation() == 1){
            holder2.item_location.setVisibility(View.VISIBLE);
        }else {
            holder2.item_location.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    //移除一条数据
    public void removeData(int position){
        weathers.remove(position);
        notifyDataSetChanged();
    }
}

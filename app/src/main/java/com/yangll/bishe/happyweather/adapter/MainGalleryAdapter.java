package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.DailyForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class MainGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DailyForecast> dailyForecasts = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public MainGalleryAdapter(){

    }

    public void bindDatas(List<DailyForecast> list){
        dailyForecasts.clear();
        if (null != list){
            dailyForecasts.addAll(list);
        }
    }

    public DailyForecast getItem(int position){
        return dailyForecasts.get(position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainGalleryHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return dailyForecasts.size();
    }
}

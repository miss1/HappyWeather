package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SearchCityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<City> cities = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public SearchCityAdapter(){

    }

    public void bindDatas(List<City> list){
        cities.clear();
        if (null != list){
            cities.addAll(list);
        }
    }

    public City getItem(int position){
        return cities.get(position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchCityHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}

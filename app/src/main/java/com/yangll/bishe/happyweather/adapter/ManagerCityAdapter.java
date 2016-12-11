package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ManagerCityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Weather> weathers = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public ManagerCityAdapter(){

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

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManagerCityHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    //移除一条数据
    public void removeData(String city){
        for (int i = 0; i < weathers.size(); i++){
            if (weathers.get(i).getBasic().getCity().equals(city)){
                weathers.remove(i);
                notifyItemRemoved(i);
            }
        }
    }
}

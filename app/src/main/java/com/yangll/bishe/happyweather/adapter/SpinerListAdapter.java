package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/11.
 */

public class SpinerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AllResponse> allResponses = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public SpinerListAdapter(){

    }

    public void bindDatas(List<AllResponse> list){
        allResponses.clear();
        if (null != list){
            allResponses.addAll(list);
        }
    }

    public AllResponse getItem(int position){
        return allResponses.get(position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpinerListHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return allResponses.size();
    }
}

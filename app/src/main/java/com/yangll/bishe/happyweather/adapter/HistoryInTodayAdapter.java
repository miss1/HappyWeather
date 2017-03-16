package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.HistoryListResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class HistoryInTodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistoryListResult> hresults = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public HistoryInTodayAdapter(){

    }

    public void bindDatas(List<HistoryListResult> list){
        hresults.clear();
        if (null != list){
            hresults.addAll(list);
        }
    }

    public HistoryListResult getItem(int position){
        return hresults.get(position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryInTodayHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return hresults.size();
    }
}

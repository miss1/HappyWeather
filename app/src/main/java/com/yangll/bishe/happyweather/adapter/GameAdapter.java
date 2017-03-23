package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<puzzle> lists = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public GameAdapter(){

    }

    public void bindDatas(List<puzzle> list){
        lists.clear();
        if (null != list){
            lists.addAll(list);
        }
    }

    public puzzle getItem(int position){
        return lists.get(position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GameHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

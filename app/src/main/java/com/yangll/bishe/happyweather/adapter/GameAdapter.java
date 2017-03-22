package com.yangll.bishe.happyweather.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bitmap> lists = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public GameAdapter(){

    }

    public void bindDatas(List<Bitmap> list){
        lists.clear();
        if (null != list){
            lists.addAll(list);
        }
    }

    public Bitmap getItem(int position){
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

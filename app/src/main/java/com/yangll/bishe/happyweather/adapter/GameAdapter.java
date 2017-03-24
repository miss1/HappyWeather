package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yangll.bishe.happyweather.bean.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有Footer的

    private List<puzzle> lists = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    private View mFooterView;

    public GameAdapter(){

    }

    public void setFooterView(View footerView){
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
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
    public int getItemViewType(int position) {
        if (mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == getItemCount() - 1){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER){
            return new FootHolder(mFooterView);
        }
        return new GameHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL){
            ((BaseViewHolder)holder).bindData(getItem(position));
        }else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView == null){
            return lists.size();
        }else {
            return lists.size() + 1;
        }
    }

    class FootHolder extends RecyclerView.ViewHolder{

        public FootHolder(View itemView) {
            super(itemView);
            return;
        }
    }
}

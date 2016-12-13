package com.yangll.bishe.happyweather.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.SpinerListAdapter;

/**
 * Created by Administrator on 2016/12/11.
 */

public class SpinerPopWindow extends PopupWindow {

    private Context context;
    private RecyclerView recyclerView;
    private SpinerListAdapter madapter;
    private LinearLayoutManager linearLayoutManager;

    public SpinerPopWindow(Context context){
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_spinerwindow, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        setBackgroundDrawable(dw);

        recyclerView = (RecyclerView) view.findViewById(R.id.spiner_recycleview);

        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setAdapter(SpinerListAdapter adapter){
        madapter = adapter;
        recyclerView.setAdapter(madapter);
    }
}

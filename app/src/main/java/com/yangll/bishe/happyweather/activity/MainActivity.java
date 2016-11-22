package com.yangll.bishe.happyweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.MainGalleryAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.bean.DailyForecast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    private List<DailyForecast> dailyForecasts = new ArrayList<>();

    @BindView(R.id.recycleview_gallery)
    RecyclerView recycleView;

    @BindView(R.id.test)
    TextView test;

    private MainGalleryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        adapter = new MainGalleryAdapter();
        adapter.bindDatas(dailyForecasts);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                test.setText(dailyForecasts.get(position).getDate());
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 7; i++){
            DailyForecast dailyForecast = new DailyForecast();
            dailyForecast.setDate("2016-"+i);
            dailyForecasts.add(dailyForecast);
        }
    }
}

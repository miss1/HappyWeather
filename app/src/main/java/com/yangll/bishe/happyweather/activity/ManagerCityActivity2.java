package com.yangll.bishe.happyweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.ManagerCityAdapter2;
import com.yangll.bishe.happyweather.adapter.OnItemRemoveListener;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.AlertDialog;
import com.yangll.bishe.happyweather.view.ItemRemoveRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerCityActivity2 extends AppCompatActivity {

    @BindView(R.id.activity_manager_city2)
    LinearLayout activity_manager_city2;

    @BindView(R.id.remove_recycle)
    ItemRemoveRecycleView remove_recycle;

    private WeatherDB weatherDB;

    private List<Weather> weathers = new ArrayList<>();
    private ManagerCityAdapter2 adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<AllResponse> allResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_city2);
        ButterKnife.bind(this);

        if (!WeatherUtil.bg.equals("")){
            activity_manager_city2.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        weatherDB = WeatherDB.getInstance(this);
        allResponses = weatherDB.getAllResponses();

        adapter = new ManagerCityAdapter2(this);
        linearLayoutManager = new LinearLayoutManager(this);

        remove_recycle.setLayoutManager(linearLayoutManager);
        remove_recycle.setAdapter(adapter);
        remove_recycle.setOnItemClickListener(new OnItemRemoveListener() {
            @Override
            public void onItemClick(View view, int position) {
                WeatherDetailActivity.actionStart(ManagerCityActivity2.this, weathers.get(position).getBasic().getCity());
            }

            @Override
            public void onDeleteClick(int position) {
                deleteCity(position);
            }
        });

        initData();
    }

    //给列表填充数据
    private void initData(){
        weathers.clear();
        for (AllResponse all:allResponses){
            if (all.getReponse() != null){
                Gson gson = new Gson();
                WeatherJson weatherJson = gson.fromJson(all.getReponse(), WeatherJson.class);
                weathers.add(weatherJson.getHeWeather5().get(0));
            }
        }
        adapter.bindDatas(weathers);
        adapter.notifyDataSetChanged();
    }

    //删除城市
    private void deleteCity(final int position){
        final String city = weathers.get(position).getBasic().getCity();
        new AlertDialog(ManagerCityActivity2.this).builder().setTitle("删除城市")
                .setMsg(city)
                .setPositiveButton("确认删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (weatherDB.getCityResponse(city).get(0).getIsLocation() == 1){
                            Toast.makeText(ManagerCityActivity2.this, "该城市为当前所在城市，不能移除",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        weatherDB.deleteCityResponse(city);
                        adapter.removeData(position);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    //跳转到添加城市页面
    @OnClick(R.id.manager_add)
    public void jumpToAdd(){
        Intent intent = new Intent(ManagerCityActivity2.this, AddCityActivity.class);
        startActivity(intent);
        finish();
    }

    //返回上一页
    @OnClick(R.id.manager_back)
    public void back(){
        finish();
    }

}

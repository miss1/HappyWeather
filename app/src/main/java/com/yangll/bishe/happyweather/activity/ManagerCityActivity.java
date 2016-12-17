package com.yangll.bishe.happyweather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.ManagerCityAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.view.AlertDialog;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.MyProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerCityActivity extends AppCompatActivity {

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    @BindView(R.id.activity_manager_city)
    RelativeLayout bg;

    private List<Weather> weathers = new ArrayList<>();
    private ManagerCityAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private WeatherDB weatherDB;

    private List<AllResponse> allResponses = new ArrayList<>();

    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置成为覆盖模式后，actionBar相当于漂浮在activity之上，不干预activity的布局
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        ab = this.getSupportActionBar();
        ab.setTitle("MangerCity");
        ab.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_manager_city);
        ButterKnife.bind(this);

        if (!WeatherUtil.bg.equals("")){
            bg.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        weatherDB = WeatherDB.getInstance(this);

        allResponses = weatherDB.getAllResponses();

        adapter = new ManagerCityAdapter();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                WeatherDetailActivity.actionStart(ManagerCityActivity.this, weathers.get(position).getBasic().getCity());
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        initData();
    }

    private void initData() {
        weathers.clear();
        for (AllResponse all:allResponses){
            if (all.getReponse() != null){
                Gson gson = new Gson();
                WeatherJson weatherJson = gson.fromJson(all.getReponse(), WeatherJson.class);
                weathers.add(weatherJson.getHeWeather5().get(0));
            }
        }
        ab.setSubtitle("update:"+WeatherUtil.month_day(weathers.get(weathers.size()-1).getBasic().getUpdate().getLoc()));
        adapter.bindDatas(weathers);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_managercity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.id_action_add:
                Intent intent = new Intent(ManagerCityActivity.this, AddCityActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.id_action_delete:
                final AlertDialog builder = new AlertDialog(ManagerCityActivity.this).builder();
                builder.setTitle("删除城市");
                builder.setInput();
                builder.setPositiveButton("确认删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String delcity = builder.getDeleteCity();
                        if (weatherDB.getCityResponse(delcity).size() <= 0){
                            Toast.makeText(ManagerCityActivity.this, "该城市不在列表中",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (weatherDB.getCityResponse(delcity).get(0).getIsLocation() == 1){
                            Toast.makeText(ManagerCityActivity.this, "该城市为当前所在城市，不能移除",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        weatherDB.deleteCityResponse(delcity);
                        adapter.removeData(delcity);
                    }
                });
                builder.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                builder.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.yangll.bishe.happyweather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.adapter.SearchCityAdapter;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.City;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.view.AlertDialog;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.view.MyProgressBar;
import com.yangll.bishe.happyweather.http.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCityActivity extends AppCompatActivity {

    private SearchCityAdapter adapter;
    private List<City> cities = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private WeatherDB weatherDB;

    @BindView(R.id.recycleview_city)
    RecyclerView recyclerView;

    private ProgressBar progressBar;

    private String city;

    @BindView(R.id.activity_add_city)
    RelativeLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置成为覆盖模式后，actionBar相当于漂浮在activity之上，不干预activity的布局
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        ActionBar ab = this.getSupportActionBar();
        ab.setTitle("AddCity");
        ab.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_add_city);
        ButterKnife.bind(this);
        if (!WeatherUtil.bg.equals("")){
            bg.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        MyProgressBar myProgressBar = new MyProgressBar();
        progressBar = myProgressBar.createMyProgressBar(this,null);

        weatherDB = WeatherDB.getInstance(this);

        adapter = new SearchCityAdapter("white");
        linearLayoutManager = new LinearLayoutManager(AddCityActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                city = cities.get(position).getCityZh();
                new AlertDialog(AddCityActivity.this).builder().setTitle("添加城市")
                        .setMsg(cities.get(position).getCityZh()+" -- "+cities.get(position).getProvinceZh())
                        .setPositiveButton("确认添加", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                qqueryWeatherNow();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }

    //从服务器查询城市的实况天气
    private void qqueryWeatherNow() {
        if (weatherDB.getCityResponse(city).size() > 0){
            Toast.makeText(AddCityActivity.this, "该城市已在列表中",Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        new HttpPost(JSONCon.SERVER_URL+JSONCon.PATH_WEATHER+"?city="+city+"&key="+JSONCon.KEY, city, nowHandler).exe();
    }

    private Handler nowHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    AllResponse allResponse = new AllResponse();
                    allResponse.setCity(city);
                    allResponse.setReponse(msg.getData().getString("response"));
                    allResponse.setIsLocation(0);
                    weatherDB.addCity(allResponse);
                    Toast.makeText(AddCityActivity.this, "添加成功",Toast.LENGTH_SHORT).show();
                    jumpToManager();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(AddCityActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

    //跳转到管理城市界面，并注销当前界面
    private void jumpToManager() {
        Intent intent = new Intent(AddCityActivity.this, ManagerCityActivity2.class);
        startActivity(intent);
        finish();
    }

    //搜索框实时搜索
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //利用SearchView此事件监听用户在搜索框中的输入文字变化，同时根据用户实时输入的文字立即返回相应的搜索结果。
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(AddCityActivity.this,"submit",Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {        //实时查询
                cities.clear();
                cities = weatherDB.findCity(newText);
                adapter.bindDatas(cities);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:       //ActionBar返回键监听事件
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

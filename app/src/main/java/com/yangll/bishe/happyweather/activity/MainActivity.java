package com.yangll.bishe.happyweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.MainGalleryAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.bean.DailyForecast;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.http.MyProgressBar;
import com.yangll.bishe.happyweather.http.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener{

    private List<DailyForecast> dailyForecasts = new ArrayList<>();

    @BindView(R.id.activity_main)
    LinearLayout activityMain;

    @BindView(R.id.recycleview_gallery)
    RecyclerView recycleView;

    @BindView(R.id.cond_txt)
    TextView condTxt;

    @BindView(R.id.weather_icon)
    ImageView weatherIcon;

    @BindView(R.id.weather_tmp)
    TextView weatherTmp;

    @BindView(R.id.update_time)
    TextView updateTime;

    private MainGalleryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<Weather> weathers;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        initToolbar();

        MyProgressBar myProgressBar = new MyProgressBar();
        progressBar = myProgressBar.createMyProgressBar(this,null);

        initData();
        initMenuFragment();

        adapter = new MainGalleryAdapter();

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                initSeclect(position);
                adapter.bindDatas(dailyForecasts);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }

    //顶部一栏
    private void initToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.refresh);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        mToolBarTextView.setText("北京");
    }

    //从服务器查询连续七天的天气信息
    private void initData() {
        progressBar.setVisibility(View.VISIBLE);
        String city = "CN101010100";
        new HttpPost(JSONCon.SERVER_URL+JSONCon.PATH_FORECAST+"?city="+city+"&key="+JSONCon.KEY, forecsatHandler).exe();
    }

    private Handler forecsatHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Gson gson = new Gson();
                    WeatherJson weatherJson = gson.fromJson((String) msg.obj, WeatherJson.class);
                    weathers = weatherJson.getHeWeather5();
                    dailyForecasts = weathers.get(0).getDaily_forecast();
                    initSeclect(0);
                    adapter.bindDatas(dailyForecasts);
                    adapter.notifyDataSetChanged();
                    Log.e("Tag", dailyForecasts.size()+"");
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(MainActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
        }
    };

    //设置选中状态,展示选中时间的天气
    private void initSeclect(int position) {
        for (DailyForecast daily : dailyForecasts){
            daily.setSeclect(false);
        }
        dailyForecasts.get(position).setSeclect(true);

        if (position == 0){
            condTxt.setText("今天："+dailyForecasts.get(position).getCond().getTxt_d());
        }else if(position == 1){
            condTxt.setText("明天："+dailyForecasts.get(position).getCond().getTxt_d());
        }else {
            condTxt.setText(WeatherUtil.month_day(dailyForecasts.get(position).getDate())+"："+dailyForecasts.get(position).getCond().getTxt_d());
        }

        weatherIcon.setBackgroundResource(WeatherUtil.getWeatherIcon(dailyForecasts.get(position).getCond().getCode_d()));
        weatherTmp.setText(dailyForecasts.get(position).getTmp().getMin()+"°"+" - "+dailyForecasts.get(position).getTmp().getMax()+"°");
        updateTime.setText("update:"+weathers.get(0).getBasic().getUpdate().getLoc());
    }

    //弹出菜单
    private void initMenuFragment(){
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int)getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
    }

    //menu的item
    private List<MenuObject> getMenuObjects(){
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject addCity = new MenuObject("添加城市");
        addCity.setResource(R.drawable.icn_add);

        MenuObject switchCity = new MenuObject("切换城市");
        switchCity.setResource(R.drawable.icn_switch);

        menuObjects.add(close);
        menuObjects.add(addCity);
        menuObjects.add(switchCity);

        return menuObjects;
    }

    //创建菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //菜单栏点击事件
    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}

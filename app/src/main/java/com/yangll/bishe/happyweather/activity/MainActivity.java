package com.yangll.bishe.happyweather.activity;

import android.content.Intent;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.MainGalleryAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.adapter.SpinerListAdapter;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.DailyForecast;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.view.MyProgressBar;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener,AMapLocationListener{

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

    @BindView(R.id.location)
    ImageView img_location;

    private MainGalleryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<Weather> weathers;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;

    private WeatherDB weatherDB;

    private ProgressBar progressBar;

    private TextView mToolBarTextView;

    private SpinerPopWindow spinerPopWindow;
    private SpinerListAdapter spinerAdapter;
    private List<AllResponse> spinerlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        MyProgressBar myProgressBar = new MyProgressBar();
        progressBar = myProgressBar.createMyProgressBar(this,null);

        weatherDB = WeatherDB.getInstance(this);

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

        //先展示上次定位城市的天气信息，稍后更新定位天气之后在更新界面
        if(weatherDB.getLocationCityResponse(1).size() > 0){
            initToolbar(weatherDB.getLocationCityResponse(1).get(0).getCity());
            showWeather(weatherDB.getLocationCityResponse(1).get(0).getReponse(),weatherDB.getLocationCityResponse(1).get(0).getCity(),1);
        }else {
            initToolbar("定位中...");
        }

        initMenuFragment();
        initLocation();

        initSpinerView();
    }

    //城市下拉框，切换城市，查看其他城市的七天预报
    private void initSpinerView() {
        if (weatherDB.getAllResponses().size() > 0){
            spinerAdapter = new SpinerListAdapter();
            spinerlist.addAll(weatherDB.getAllResponses());
            spinerAdapter.bindDatas(spinerlist);

            spinerPopWindow = new SpinerPopWindow(MainActivity.this);
            spinerPopWindow.setAdapter(spinerAdapter);

            spinerAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
                @Override
                public void onItemClick(int position) {
                    String selectcity = spinerlist.get(position).getCity();
                    String response = spinerlist.get(position).getReponse();
                    int location = spinerlist.get(position).getIsLocation();

                    if (response == null || "".equals(response)){
                        progressBar.setVisibility(View.VISIBLE);
                        initData(selectcity, location);
                    }else {
                        showWeather(response, selectcity, location);
                    }
                    spinerPopWindow.dismiss();
                }

                @Override
                public boolean onItemLongClick(int position) {
                    return false;
                }
            });
        }
    }

    //顶部一栏
    private void initToolbar(String cityname){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
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
                progressBar.setVisibility(View.VISIBLE);
                refreshAllWeather();
            }
        });
        mToolBarTextView.setText(cityname);
    }

    //点击顶部城市名称弹出下拉框切换城市
    @OnClick(R.id.text_view_toolbar_title)
    public void changeCity(View view){
        spinerPopWindow.setWidth(mToolBarTextView.getWidth());
        spinerPopWindow.setHeight(300);
        spinerPopWindow.showAsDropDown(mToolBarTextView);
    }

    //定位相关设置
    private void initLocation() {
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mapLocationClientOption.setOnceLocation(true);
        mapLocationClientOption.setNeedAddress(true);
        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.setLocationListener(this);
        mapLocationClient.startLocation();
    }

    //定位监听，获取定位结果
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                String locationcity;
                if(aMapLocation.getCity().length() > 2){
                    locationcity = aMapLocation.getCity().substring(0, aMapLocation.getCity().length() - 1);
                }else {
                    locationcity = aMapLocation.getCity();
                }
                //当前定位城市已经存在在已添加城市中
                if(weatherDB.getCityResponse(locationcity).size() > 0){
                    //上次定位城市与当前定位城市不同，修改定位标记
                    if (weatherDB.getCityResponse(locationcity).get(0).getIsLocation() == 0){
                        weatherDB.updateLocation(locationcity);
                    }
                    mToolBarTextView.setText(locationcity);
                    refreshAllWeather();
                    //initData(locationcity, 1);
                }else {
                    //当前定位城市不存在在已添加城市中
                    weatherDB.clearLocation();
                    initData(locationcity, 1);
                }

            }else {            //定位失败
                if (weatherDB.getAllResponses().size() > 0 && weatherDB.getLocationCityResponse(1).size() > 0){
                    mToolBarTextView.setText(weatherDB.getLocationCityResponse(1).get(0).getCity());
                    refreshAllWeather();
                }else {
                    initData("北京", 1);
                }
                Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    //更新所有添加城市的七天预报
    private void refreshAllWeather(){
        for(AllResponse resp : weatherDB.getAllResponses()){
            new HttpPost(JSONCon.SERVER_URL+JSONCon.PATH_WEATHER+"?city="+resp.getCity()+"&key="+JSONCon.KEY, resp.getIsLocation(), resp.getCity(), refreshHandler).exe();
        }
    }

    //从服务器查询连续七天的天气信息
    private void initData(String cityname, int islocation) {
        //progressBar.setVisibility(View.VISIBLE);
        new HttpPost(JSONCon.SERVER_URL+JSONCon.PATH_WEATHER+"?city="+cityname+"&key="+JSONCon.KEY, islocation, cityname, forecsatHandler).exe();
    }

    private Handler forecsatHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    //将预报信息缓存更新到数据库
                    String city = msg.getData().getString("cityname");
                    String response = msg.getData().getString("response");

                    AllResponse allResponse = new AllResponse();
                    allResponse.setCity(city);
                    allResponse.setReponse(response);
                    allResponse.setIsLocation(msg.arg1);
                    weatherDB.addCity(allResponse);

                    showWeather(response, city, msg.arg1);
                    refreshAllWeather();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(MainActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
        }
    };

    private Handler refreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    //将预报信息缓存更新到数据库
                    String city = msg.getData().getString("cityname");
                    String response = msg.getData().getString("response");
                    weatherDB.updateWeatherResponse(city, response);
                    if(mToolBarTextView.getText().toString().equals(city)){
                        showWeather(response, city, msg.arg1);
                    }
                    //发送广播通知widget更新
                    Intent intent = new Intent().setAction(JSONCon.BRODCAST_UPDATE);
                    MainActivity.this.sendBroadcast(intent);
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(MainActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
        }
    };

    //解析返回的数据并显示到界面(七天天气)
    private void showWeather(String response, String cityy, int islocation){
        mToolBarTextView.setText(cityy);
        if (islocation == 1){
            img_location.setVisibility(View.VISIBLE);
        }else {
            img_location.setVisibility(View.GONE);
        }
        //解析返回的json数据
        Gson gson = new Gson();
        WeatherJson weatherJson = gson.fromJson(response, WeatherJson.class);
        weathers = weatherJson.getHeWeather5();
        dailyForecasts = weathers.get(0).getDaily_forecast();
        WeatherUtil.bg = dailyForecasts.get(0).getCond().getCode_d();  //将背景码存起来
        //讲解析后的数据显示到界面
        initSeclect(0);
        adapter.bindDatas(dailyForecasts);
        adapter.notifyDataSetChanged();
        Log.e("Tag", dailyForecasts.size()+"");
    }

    //设置选中状态,展示选中时间的天气
    private void initSeclect(int position) {
        for (DailyForecast daily : dailyForecasts){
            daily.setSeclect(false);
        }
        dailyForecasts.get(position).setSeclect(true);

        activityMain.setBackgroundResource(WeatherUtil.getWeatherBg(dailyForecasts.get(position).getCond().getCode_d()));

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

        MenuObject detailWeather = new MenuObject("天气详情");
        detailWeather.setResource(R.drawable.icn_detail);

        MenuObject switchCity = new MenuObject("管理城市");
        switchCity.setResource(R.drawable.icn_switch);

        menuObjects.add(close);
        menuObjects.add(addCity);
        menuObjects.add(detailWeather);
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
        Intent intent = new Intent();
        switch (position){
            case 1:
                intent.setClass(MainActivity.this, AddCityActivity.class);
                startActivity(intent);
                break;
            case 2:
                WeatherDetailActivity.actionStart(MainActivity.this, mToolBarTextView.getText().toString());
                break;
            case 3:
                intent.setClass(MainActivity.this, ManagerCityActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        if(weatherDB.getAllResponses().size() > 0){
            spinerlist.clear();
            spinerlist.addAll(weatherDB.getAllResponses());
            spinerAdapter.bindDatas(spinerlist);
            spinerAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁定位客户端
        if (null != mapLocationClient){
            mapLocationClient.onDestroy();
            mapLocationClient = null;
            mapLocationClient = null;
        }
    }
}

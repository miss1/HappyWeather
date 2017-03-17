package com.yangll.bishe.happyweather.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.City;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.SelectCityCallbackListener;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.SelectCityDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherPkActivity extends AppCompatActivity {

    @BindView(R.id.activity_weather_pk)
    RelativeLayout activity_weather_pk;

    @BindView(R.id.pk_leftcity)
    TextView pk_leftcity;

    @BindView(R.id.pk_rightcity)
    TextView pk_rightcity;

    private List<City> cities = new ArrayList<>();
    private WeatherDB weatherDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_pk);
        ButterKnife.bind(this);

        if (!WeatherUtil.bg.equals("")){
            activity_weather_pk.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        weatherDB = WeatherDB.getInstance(this);
        cities = weatherDB.loadAllCity();
    }

    //关闭页面
    @OnClick(R.id.pk_close)
    public void back(View view){
        finish();
    }

    //选择左边的城市
    @OnClick(R.id.pk_leftcity)
    public void selectcityL(View view){
        SelectCityCallbackListener selectCityCallbackListener = new SelectCityCallbackListener() {
            @Override
            public void onFinish(String cityname) {
                pk_leftcity.setText(cityname);
            }
        };
        new SelectCityDialog(WeatherPkActivity.this, selectCityCallbackListener).builder().initData().show();
    }

    //选择右边的城市
    @OnClick(R.id.pk_rightcity)
    public void selectcityR(View view){
        SelectCityCallbackListener selectCityCallbackListener = new SelectCityCallbackListener() {
            @Override
            public void onFinish(String cityname) {
                pk_rightcity.setText(cityname);
            }
        };
        new SelectCityDialog(WeatherPkActivity.this, selectCityCallbackListener).builder().initData().show();
    }

    //开始PK
    @OnClick(R.id.pk_begin)
    public void begin(View view){
        String cityleft = pk_leftcity.getText().toString();
        String cityRight = pk_rightcity.getText().toString();
        if (cityleft != "？" && cityRight != "？" && cityleft != cityRight){
            WeatherPkActivity2.actionStart(WeatherPkActivity.this, cityleft, cityRight);
        }else {
            Toast.makeText(WeatherPkActivity.this, "请先选择要pk的城市", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.yangll.bishe.happyweather.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_city)
    TextView detail_city;

    @BindView(R.id.detail_time)
    TextView detail_time;

    @BindView(R.id.detail_tmp)
    TextView detail_tmp;

    @BindView(R.id.detail_fl)
    TextView detail_fl;

    @BindView(R.id.detail_hum)
    TextView detail_hum;

    @BindView(R.id.detail_wind)
    TextView detail_wind;

    @BindView(R.id.detail_pcpn)
    TextView detail_pcpn;

    @BindView(R.id.detail_vis)
    TextView detail_vis;

    @BindView(R.id.detail_img)
    ImageView detail_img;

    @BindView(R.id.detail_suggtion_brf)
    TextView suggtion_brf;

    @BindView(R.id.detail_suggtion_txt)
    TextView suggtion_txt;

    @BindView(R.id.detail_comf)
    TextView detail_comf;

    @BindView(R.id.detail_drsg)
    TextView detail_drsg;

    @BindView(R.id.detail_cw)
    TextView detail_cw;

    @BindView(R.id.detail_flu)
    TextView detail_flu;

    @BindView(R.id.detail_sport)
    TextView detail_sport;

    @BindView(R.id.detail_trav)
    TextView detail_trav;

    @BindView(R.id.detail_uv)
    TextView detail_uv;

    @BindView(R.id.activity_weather_detail)
    LinearLayout bg;

    private String city;

    private WeatherDB weatherDB;

    private Weather weather;

    private List<AllResponse> allResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        ButterKnife.bind(this);

        if (!WeatherUtil.bg.equals("")){
            bg.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        Intent intent = getIntent();
        city = intent.getStringExtra("cityname");
        detail_city.setText(city);

        weatherDB = WeatherDB.getInstance(this);

        allResponses = weatherDB.getCityResponse(city);

        resetSelect();

        if (allResponses.size() > 0){
            Gson gson = new Gson();
            WeatherJson weatherJson = gson.fromJson(allResponses.get(0).getReponse(), WeatherJson.class);
            weather = weatherJson.getHeWeather5().get(0);
            initView();
        }
    }

    //重置suggestion模块的选中状态
    private void resetSelect() {
        detail_comf.setBackgroundColor(0);
        detail_drsg.setBackgroundColor(0);
        detail_cw.setBackgroundColor(0);
        detail_flu.setBackgroundColor(0);
        detail_sport.setBackgroundColor(0);
        detail_trav.setBackgroundColor(0);
        detail_uv.setBackgroundColor(0);
    }

    //初始化，将天气详细信息显示出来
    private void initView() {
        detail_time.setText("today: " + WeatherUtil.month_day(weather.getDaily_forecast().get(0).getDate()));
        detail_tmp.setText("温度："+weather.getNow().getTmp() + "°");
        detail_fl.setText("体感温度："+weather.getNow().getFl() + "°");
        detail_hum.setText("相对湿度："+weather.getNow().getHum() + "%");
        detail_wind.setText("风力风向："+weather.getNow().getWind().getDir()+weather.getNow().getWind().getSc() + "级");
        detail_pcpn.setText("降水量："+weather.getNow().getPcpn() + "mm");
        detail_vis.setText("能见度："+weather.getNow().getVis() + "km");
        detail_img.setBackgroundResource(WeatherUtil.getWeatherIcon(weather.getDaily_forecast().get(0).getCond().getCode_d()));
        detail_comf.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getComf().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getComf().getTxt());
    }

    //以下是点击切换suggestion模块
    @OnClick(R.id.detail_comf)
    public void chooseComf(){
        resetSelect();
        detail_comf.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getComf().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getComf().getTxt());
    }

    @OnClick(R.id.detail_drsg)
    public void chooseDrsg(){
        resetSelect();
        detail_drsg.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getDrsg().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getDrsg().getTxt());
    }

    @OnClick(R.id.detail_cw)
    public void chooseCw(){
        resetSelect();
        detail_cw.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getCw().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getCw().getTxt());
    }

    @OnClick(R.id.detail_flu)
    public void chooseFlu(){
        resetSelect();
        detail_flu.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getFlu().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getFlu().getTxt());
    }

    @OnClick(R.id.detail_sport)
    public void chooseSport(){
        resetSelect();
        detail_sport.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getSport().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getSport().getTxt());
    }

    @OnClick(R.id.detail_trav)
    public void chooseTrav(){
        resetSelect();
        detail_trav.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getTrav().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getTrav().getTxt());
    }

    @OnClick(R.id.detail_uv)
    public void chooseUv(){
        resetSelect();
        detail_uv.setBackgroundColor(Color.parseColor("#7f0080ff"));
        suggtion_brf.setText(weather.getSuggestion().getUv().getBrf());
        suggtion_txt.setText(weather.getSuggestion().getUv().getTxt());
    }

    public static void actionStart(Context context, String city){
        Intent intent = new Intent(context, WeatherDetailActivity.class);
        intent.putExtra("cityname", city);
        context.startActivity(intent);
    }
}

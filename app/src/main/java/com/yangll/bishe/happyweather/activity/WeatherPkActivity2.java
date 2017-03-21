package com.yangll.bishe.happyweather.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.Now;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.http.ProgressbarTask;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.MyProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherPkActivity2 extends AppCompatActivity {

    @BindView(R.id.activity_weather_pk2)
    LinearLayout activity_weather_pk2;

    @BindView(R.id.cityleft)
    TextView cityleft;

    @BindView(R.id.cityright)
    TextView cityright;

    /*温度*/
    @BindView(R.id.pro_tmp_l)
    ProgressBar pro_tmp_l;

    @BindView(R.id.pro_tmp_r)
    ProgressBar pro_tmp_r;

    @BindView(R.id.pro_tmp_l_txt)
    TextView pro_tmp_l_txt;

    @BindView(R.id.pro_tmp_r_txt)
    TextView pro_tmp_r_txt;

    @BindView(R.id.tmp_value)
    RelativeLayout tmp_value;

    @BindView(R.id.tmp_value_l)
    TextView tmp_value_l;

    @BindView(R.id.tmp_value_r)
    TextView tmp_value_r;

    @BindView(R.id.tmp_img_l)
    ImageView tmp_img_l;

    @BindView(R.id.tmp_img_r)
    ImageView tmp_img_r;

    /*能见度*/
    @BindView(R.id.pro_vis_l)
    ProgressBar pro_vis_l;

    @BindView(R.id.pro_vis_r)
    ProgressBar pro_vis_r;

    @BindView(R.id.pro_vis_l_txt)
    TextView pro_vis_l_txt;

    @BindView(R.id.pro_vis_r_txt)
    TextView pro_vis_r_txt;

    @BindView(R.id.vis_value)
    RelativeLayout vis_value;

    @BindView(R.id.vis_value_l)
    TextView vis_value_l;

    @BindView(R.id.vis_value_r)
    TextView vis_value_r;

    @BindView(R.id.vis_img_l)
    ImageView vis_img_l;

    @BindView(R.id.vis_img_r)
    ImageView vis_img_r;

    /*相对湿度*/
    @BindView(R.id.pro_hum_l)
    ProgressBar pro_hum_l;

    @BindView(R.id.pro_hum_r)
    ProgressBar pro_hum_r;

    @BindView(R.id.pro_hum_l_txt)
    TextView pro_hum_l_txt;

    @BindView(R.id.pro_hum_r_txt)
    TextView pro_hum_r_txt;

    @BindView(R.id.hum_value)
    RelativeLayout hum_value;

    @BindView(R.id.hum_value_l)
    TextView hum_value_l;

    @BindView(R.id.hum_value_r)
    TextView hum_value_r;

    @BindView(R.id.hum_img_l)
    ImageView hum_img_l;

    @BindView(R.id.hum_img_r)
    ImageView hum_img_r;

    /*风速*/
    @BindView(R.id.pro_spd_l)
    ProgressBar pro_spd_l;

    @BindView(R.id.pro_spd_r)
    ProgressBar pro_spd_r;

    @BindView(R.id.pro_spd_l_txt)
    TextView pro_spd_l_txt;

    @BindView(R.id.pro_spd_r_txt)
    TextView pro_spd_r_txt;

    @BindView(R.id.spd_value)
    RelativeLayout spd_value;

    @BindView(R.id.spd_value_l)
    TextView spd_value_l;

    @BindView(R.id.spd_value_r)
    TextView spd_value_r;

    @BindView(R.id.spd_img_l)
    ImageView spd_img_l;

    @BindView(R.id.spd_img_r)
    ImageView spd_img_r;

    @BindView(R.id.pk_conclosion)
    TextView pk_conclosion;

    private String cityL;
    private String cityR;
    private int width;

    private WeatherDB weatherDB;
    private Now leftnows;
    private Now rightnows;
    private List<AllResponse> leftallResponses = new ArrayList<>();
    private List<AllResponse> rightallResponses = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_pk2);
        ButterKnife.bind(this);

        if (!"".equals(WeatherUtil.bg)){
            activity_weather_pk2.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        Intent intent = getIntent();
        cityL = intent.getStringExtra("cityL");
        cityR = intent.getStringExtra("cityR");

        weatherDB = WeatherDB.getInstance(this);
        leftallResponses = weatherDB.getCityResponse(cityL);
        rightallResponses = weatherDB.getCityResponse(cityR);

        MyProgressBar myProgressBar = new MyProgressBar();
        progressBar = myProgressBar.createMyProgressBar(this,null);

        cityleft.setText(cityL);
        cityright.setText(cityR);

        //获取天气数据
        if (leftallResponses.size() > 0 && rightallResponses.size() > 0){
            Gson gson = new Gson();
            WeatherJson lweatherJson = gson.fromJson(leftallResponses.get(0).getReponse(), WeatherJson.class);
            WeatherJson rweatherJson = gson.fromJson(rightallResponses.get(0).getReponse(), WeatherJson.class);
            leftnows = lweatherJson.getHeWeather5().get(0).getNow();
            rightnows = rweatherJson.getHeWeather5().get(0).getNow();
            messureProWidth();
        }else if (leftallResponses.size() > 0){
            Gson gson = new Gson();
            WeatherJson lweatherJson = gson.fromJson(leftallResponses.get(0).getReponse(), WeatherJson.class);
            leftnows = lweatherJson.getHeWeather5().get(0).getNow();
            queryRightNow();
        }else if (rightallResponses.size() > 0){
            Gson gson = new Gson();
            WeatherJson rweatherJson = gson.fromJson(rightallResponses.get(0).getReponse(), WeatherJson.class);
            rightnows = rweatherJson.getHeWeather5().get(0).getNow();
            queryLeftNow();
        }else {
            queryLeftNow();
            queryRightNow();
        }

    }

    //从服务器查询左边城市天气
    private void queryLeftNow(){
        progressBar.setVisibility(View.VISIBLE);
        new HttpPost(JSONCon.SERVER_URL+JSONCon.PATH_NOW+"?city="+cityL+"&key="+JSONCon.KEY, leftHandler).exe();
    }

    //从服务器查询右边城市天气
    private void queryRightNow(){
        progressBar.setVisibility(View.VISIBLE);
        new HttpPost(JSONCon.SERVER_URL+JSONCon.PATH_NOW+"?city="+cityR+"&key="+JSONCon.KEY, rightHandler).exe();
    }

    private Handler leftHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Gson gson = new Gson();
                    WeatherJson lweatherJson = gson.fromJson(msg.obj.toString(), WeatherJson.class);
                    leftnows = lweatherJson.getHeWeather5().get(0).getNow();
                    Log.e("left", leftnows.getHum());
                    messureProWidth();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(WeatherPkActivity2.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

    private Handler rightHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Gson gson = new Gson();
                    WeatherJson rweatherJson = gson.fromJson(msg.obj.toString(), WeatherJson.class);
                    rightnows = rweatherJson.getHeWeather5().get(0).getNow();
                    Log.e("right", rightnows.getHum());
                    messureProWidth();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(WeatherPkActivity2.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

    //测量进度条的总长度
    private void messureProWidth(){
        if (leftnows == null || rightnows == null){
            return;
        }
        ViewTreeObserver vto2 = pro_tmp_l.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pro_tmp_l.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.e("prow", pro_tmp_l.getWidth()+"");
                width = pro_tmp_l.getWidth();
                showProgress(width);
            }
        });
    }

    //画进度条
    private void showProgress(int width){
        //温度
        tmp_value_l.setText(leftnows.getTmp() + "°");
        tmp_value_r.setText(rightnows.getTmp() + "°");
        if (Integer.parseInt(leftnows.getTmp()) > Integer.parseInt(rightnows.getTmp())){
            tmp_img_l.setVisibility(View.VISIBLE);
        }else {
            tmp_img_r.setVisibility(View.VISIBLE);
        }
        new ProgressbarTask(pro_tmp_l, pro_tmp_r, caculateValueL(leftnows.getTmp(), rightnows.getTmp()), pro_tmp_l_txt, pro_tmp_r_txt, width, tmp_value).execute();

        //能见度
        vis_value_l.setText(leftnows.getVis() + "km");
        vis_value_r.setText(rightnows.getVis() + "km");
        if (Integer.parseInt(leftnows.getVis()) > Integer.parseInt(rightnows.getVis())){
            vis_img_l.setVisibility(View.VISIBLE);
        }else {
            vis_img_r.setVisibility(View.VISIBLE);
        }
        new ProgressbarTask(pro_vis_l, pro_vis_r, caculateValueL(leftnows.getVis(), rightnows.getVis()), pro_vis_l_txt, pro_vis_r_txt, width, vis_value).execute();

        //相对湿度
        hum_value_l.setText(leftnows.getHum() + "%");
        hum_value_r.setText(rightnows.getHum() + "%");
        if (Integer.parseInt(leftnows.getHum()) > Integer.parseInt(rightnows.getHum())){
            hum_img_l.setVisibility(View.VISIBLE);
        }else {
            hum_img_r.setVisibility(View.VISIBLE);
        }
        new ProgressbarTask(pro_hum_l, pro_hum_r, caculateValueL(leftnows.getHum(), rightnows.getHum()), pro_hum_l_txt, pro_hum_r_txt, width, hum_value).execute();

        //风速
        spd_value_l.setText(leftnows.getWind().getSpd() + "kmph");
        spd_value_r.setText(rightnows.getWind().getSpd() + "kmph");
        if (Integer.parseInt(leftnows.getWind().getSpd()) > Integer.parseInt(rightnows.getWind().getSpd())){
            spd_img_l.setVisibility(View.VISIBLE);
        }else {
            spd_img_r.setVisibility(View.VISIBLE);
        }
        new ProgressbarTask(pro_spd_l, pro_spd_r, caculateValueL(leftnows.getWind().getSpd(), rightnows.getWind().getSpd()), pro_spd_l_txt, pro_spd_r_txt, width, spd_value, pk_conclosion).execute();

    }

    //计算左边进度条的最大示数
    private int caculateValueL(String sl, String sr){
        float valueL;
        int vl = Integer.parseInt(sl);
        int vr = Integer.parseInt(sr);
        if (vl >=0 && vr >= 0){
            valueL = vl*100/(vl+vr);
        }else if (vl < 0 && vr < 0){
            vl = Math.abs(vl);
            vr = Math.abs(vr);
            valueL = vr*100/(vl+vr);
        }else {
            if (vl < 0){
                vl = Math.abs(vl);
                vr = vr + 2 * vl;
            }else {
                vr = Math.abs(vr);
                vl = vl + 2 * vr;
            }
            valueL = vl*100/(vl+vr);
        }
        return (int) valueL;
    }

    //关闭页面
    @OnClick(R.id.pk_close)
    public void back(View view){
        finish();
    }

    public static void actionStart(Context context, String cityl, String cityr){
        Intent intent = new Intent(context, WeatherPkActivity2.class);
        intent.putExtra("cityL", cityl);
        intent.putExtra("cityR", cityr);
        context.startActivity(intent);
    }
}

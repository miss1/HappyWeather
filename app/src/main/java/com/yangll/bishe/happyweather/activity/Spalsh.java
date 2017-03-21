package com.yangll.bishe.happyweather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.City;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;

import java.util.List;

import cn.bmob.v3.Bmob;

public class Spalsh extends AppCompatActivity {

    private WeatherDB weatherDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        Bmob.initialize(this, JSONCon.APPLICATION_ID);

        weatherDB = WeatherDB.getInstance(this);

        //如果已经将所有城市信息存储到数据库，则延迟两秒跳到主界面;否则查询并存储城市新之后跳到主界面
        if (weatherDB.loadAllCity().size() > 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToMain();
                }
            }, 2000);
        }else {
            new HttpPost(JSONCon.CITY_URL, cityListHandler).exe();
        }
    }

    private Handler cityListHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Log.e("Tag", (String) msg.obj);
                    Gson gson = new Gson();
                    String obj = (String) msg.obj;
                    String response = "["+obj.split("\\[")[1];
                    List<City> cityList = gson.fromJson(response, new TypeToken<List<City>>(){}.getType());
                    weatherDB.saveCities(cityList);
                    jumpToMain();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(Spalsh.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void jumpToMain() {
        Intent inetent = new Intent(Spalsh.this, MainActivity.class);
        startActivity(inetent);
        finish();
    }
}

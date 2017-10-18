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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.bmob.v3.Bmob;

public class Spalsh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        Bmob.initialize(this, JSONCon.APPLICATION_ID);

        importDataBase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        }, 2000);

    }

    private void jumpToMain() {
        Intent inetent = new Intent(Spalsh.this, MainActivity.class);
        startActivity(inetent);
        finish();
    }

    //导入数据库
    public void importDataBase(){
        final String DATABASE_PATH="data/data/"+ "com.yangll.bishe.happyweather" + "/databases/";
        String databaseFile=DATABASE_PATH+"citydb.db";
        //创建databases目录（不存在时）
        File file=new File(DATABASE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        //判断数据库是否存在
        if (!new File(databaseFile).exists()) {
            //把数据库拷贝到/data/data/<package_name>/databases目录下
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(databaseFile);
                //数据库放assets目录下
                //InputStream inputStream = getAssets().open("mydb.db");
                //数据库方res/rew目录下
                InputStream inputStream=getResources().openRawResource(R.raw.citydb);
                byte[] buffer = new byte[1024];
                int readBytes = 0;

                while ((readBytes = inputStream.read(buffer)) != -1)
                    fileOutputStream.write(buffer, 0, readBytes);

                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
            }
        }
    }
}

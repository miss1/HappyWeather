package com.yangll.bishe.happyweather.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yangll.bishe.happyweather.R;

import butterknife.ButterKnife;

public class WeatherPkActivity2 extends AppCompatActivity {

    private String cityL;
    private String cityR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_pk2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        cityL = intent.getStringExtra("cityL");
        cityR = intent.getStringExtra("cityR");

        Toast.makeText(WeatherPkActivity2.this, cityL + "," + cityR, Toast.LENGTH_SHORT).show();
    }

    public static void actionStart(Context context, String cityl, String cityr){
        Intent intent = new Intent(context, WeatherPkActivity2.class);
        intent.putExtra("cityL", cityl);
        intent.putExtra("cityR", cityr);
        context.startActivity(intent);
    }
}

package com.yangll.bishe.happyweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.db.WeatherDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerCityActivity extends AppCompatActivity {

    @BindView(R.id.tv1)
    TextView tv1;

    private WeatherDB weatherDB;

    private List<AllResponse> allResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_city);
        ButterKnife.bind(this);

        weatherDB = WeatherDB.getInstance(this);

        allResponses = weatherDB.getAllResponses();

        String a = "";
        for (AllResponse all:allResponses){
            if (all.getNow() != null){
                a += all.getNow();
            }
        }
        tv1.setText(a);
    }
}

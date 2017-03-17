package com.yangll.bishe.happyweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.DailyForecast;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.DrawLineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.view.LineChartView;

public class LineChartActivity extends AppCompatActivity {

    @BindView(R.id.activity_line_chart)
    LinearLayout activity_line_chart;

    @BindView(R.id.line_city)
    TextView line_city;

    @BindView(R.id.line_maxtmp)
    LineChartView line_maxtmp;

    @BindView(R.id.line_mintmp)
    LineChartView line_mintmp;

    @BindView(R.id.line_vis)
    LineChartView line_vis;

    @BindView(R.id.line_sr)
    LineChartView line_sr;

    @BindView(R.id.line_ss)
    LineChartView line_ss;

    private String city;

    private WeatherDB weatherDB;
    private List<DailyForecast> dailyForecasts = new ArrayList<>();
    private List<AllResponse> allResponses = new ArrayList<>();

    private String[] date = new String[7];
    private int[] maxTmp= new int[7];
    private int[] minTmp = new int[7];
    private int[] vis = new int[7];
    private String[] sr = new String[7];
    private String[] ss = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        line_city.setText(city);

        if (!WeatherUtil.bg.equals("")){
            activity_line_chart.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        weatherDB = WeatherDB.getInstance(this);
        allResponses = weatherDB.getCityResponse(city);

        if (allResponses.size() > 0){
            Gson gson = new Gson();
            WeatherJson weatherJson = gson.fromJson(allResponses.get(0).getReponse(), WeatherJson.class);
            dailyForecasts = weatherJson.getHeWeather5().get(0).getDaily_forecast();
            initDate();
            drawLines();
        }

    }

    //填充数据
    private void initDate(){
        for (int i = 0; i < 7; i++){
            date[i] = WeatherUtil.month_day(dailyForecasts.get(i).getDate());
            maxTmp[i] = Integer.parseInt(dailyForecasts.get(i).getTmp().getMax());
            minTmp[i] = Integer.parseInt(dailyForecasts.get(i).getTmp().getMin());
            vis[i] = Integer.parseInt(dailyForecasts.get(i).getVis());
            sr[i] = dailyForecasts.get(i).getAstro().getSr();
            ss[i] = dailyForecasts.get(i).getAstro().getSs();
        }
    }

    //画折线
    private void drawLines(){
        new DrawLineChart(line_maxtmp, date, maxTmp, true);
        new DrawLineChart(line_mintmp, date, minTmp, true);
        new DrawLineChart(line_sr, date, sr);
        new DrawLineChart(line_ss, date, ss);
        new DrawLineChart(line_vis, date, vis, false);
    }

    //返回事件
    @OnClick(R.id.line_back)
    public void lineback(View view){
        finish();
    }

    public static void actioStart(Context context, String city){
        Intent intent = new Intent(context, LineChartActivity.class);
        intent.putExtra("city", city);
        context.startActivity(intent);
    }
}

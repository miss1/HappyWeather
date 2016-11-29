package com.yangll.bishe.happyweather.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class Weather {

    private Basic basic;
    private String status;
    private List<DailyForecast> daily_forecast;

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DailyForecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }
}

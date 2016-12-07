package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/12/1.
 */

public class AllResponse {

    private String city;
    private String forecast;
    private String now;
    private String suggestion;
    private int isLocation;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public int getIsLocation() {
        return isLocation;
    }

    public void setIsLocation(int isLocation) {
        this.isLocation = isLocation;
    }
}

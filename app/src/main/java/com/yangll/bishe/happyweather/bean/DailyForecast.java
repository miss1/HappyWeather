package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/11/22.
 * 7-10天天气预报
 */

public class DailyForecast {

    private Cond cond;              //天气状况
    private String date;            //预报日期
    private String hum;             //相对湿度（%）
    private Tmp tmp;                //温度
    private Wind wind;              //风力风向
    private String vis;             //能见度
    private Astro astro;

    private boolean isSeclect;      //是否选中

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public boolean isSeclect() {
        return isSeclect;
    }

    public void setSeclect(boolean seclect) {
        isSeclect = seclect;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }
}

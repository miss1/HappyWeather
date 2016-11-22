package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/11/22.
 * 风力风向
 */

public class Wind {

    private String deg;              //风向（360度）
    private String dir;              //风向(北风)
    private String sc;               //风力等级
    private String spd;              //风速（kmph）

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }
}

package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/12/8.
 */

public class Now {

    private Cond cond;                 //天气状况
    private String fl;                 //体感温度
    private String hum;                //相对湿度（%）
    private String pcpn;               //降水量（mm）
    private String pres;               //气压
    private String tmp;                //温度
    private String vis;                //能见度（km）
    private Wind wind;                 //风力风向

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}

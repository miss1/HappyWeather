package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/11/25.
 */

public class Basic {

    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private String prov;
    private Updata update;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public Updata getUpdate() {
        return update;
    }

    public void setUpdate(Updata update) {
        this.update = update;
    }
}

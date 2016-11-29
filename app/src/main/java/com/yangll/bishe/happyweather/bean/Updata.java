package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/11/25.
 */

public class Updata {
    /**
     * 当地时间
     */
    private String loc;

    /**
    * UTC时间
    */
    private String utc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}

package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/12/17.
 */

public class Suggestion {
    private SuggesDetail comf;                    //舒适度指数
    private SuggesDetail cw;                      //洗车指数
    private SuggesDetail drsg;                    //穿衣指数
    private SuggesDetail flu;                     //感冒指数
    private SuggesDetail sport;                   //运动指数
    private SuggesDetail trav;                    //旅游指数
    private SuggesDetail uv;                      //紫外线指数

    public SuggesDetail getComf() {
        return comf;
    }

    public void setComf(SuggesDetail comf) {
        this.comf = comf;
    }

    public SuggesDetail getCw() {
        return cw;
    }

    public void setCw(SuggesDetail cw) {
        this.cw = cw;
    }

    public SuggesDetail getDrsg() {
        return drsg;
    }

    public void setDrsg(SuggesDetail drsg) {
        this.drsg = drsg;
    }

    public SuggesDetail getFlu() {
        return flu;
    }

    public void setFlu(SuggesDetail flu) {
        this.flu = flu;
    }

    public SuggesDetail getSport() {
        return sport;
    }

    public void setSport(SuggesDetail sport) {
        this.sport = sport;
    }

    public SuggesDetail getTrav() {
        return trav;
    }

    public void setTrav(SuggesDetail trav) {
        this.trav = trav;
    }

    public SuggesDetail getUv() {
        return uv;
    }

    public void setUv(SuggesDetail uv) {
        this.uv = uv;
    }
}

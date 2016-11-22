package com.yangll.bishe.happyweather.bean;

/**
 * Created by Administrator on 2016/11/22.
 * 天气状况
 */

public class Cond {

    private String code_d;     //白天天气状况代码
    private String code_n;     //夜间天气状况代码
    private String txt_d;      //白天天气状况描述
    private String txt_n;      //夜间天气状况描述

    public String getCode_d() {
        return code_d;
    }

    public void setCode_d(String code_d) {
        this.code_d = code_d;
    }

    public String getCode_n() {
        return code_n;
    }

    public void setCode_n(String code_n) {
        this.code_n = code_n;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public String getTxt_n() {
        return txt_n;
    }

    public void setTxt_n(String txt_n) {
        this.txt_n = txt_n;
    }
}

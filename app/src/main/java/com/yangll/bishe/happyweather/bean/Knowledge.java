package com.yangll.bishe.happyweather.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/21.
 */

public class knowledge extends BmobObject {

    private String sing;
    private String content;

    public String getSing() {
        return sing;
    }

    public void setSing(String sing) {
        this.sing = sing;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

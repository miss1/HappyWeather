package com.yangll.bishe.happyweather.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class HistoryDetailResult {

    private String e_id;
    private String title;
    private String content;
    private String picNo;
    private List<HistoryPicUrl> picUrl;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicNo() {
        return picNo;
    }

    public void setPicNo(String picNo) {
        this.picNo = picNo;
    }

    public List<HistoryPicUrl> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<HistoryPicUrl> picUrl) {
        this.picUrl = picUrl;
    }
}

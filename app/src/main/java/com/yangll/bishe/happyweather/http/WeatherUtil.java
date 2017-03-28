package com.yangll.bishe.happyweather.http;

import android.content.Context;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.knowledge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */

public class WeatherUtil {

    public static String bg = "";

    public static List<knowledge> list = new ArrayList<>();

    /*
    * dip转换成pix
    */
    public static int dip2px(Context context, float dpvalue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpvalue * scale + 0.5f);
    }

    /*
    * 将时间去掉年份显示出来
    */
    public static String month_day(String time){
        String[] datas = time.split("-");
        String data = datas[1] + "-" + datas[2];
        return data;
    }

    /*
    * 天气背景
    */
    public static int getWeatherBg(String code){
        int icon;
        switch (code.charAt(0)){
            case '1':
                icon = R.drawable.bg_sunny;
                break;
            case '2':
                icon = R.drawable.bg_thunder;
                break;
            case '3':
                icon = R.drawable.bg_rain;
                break;
            case '4':
                icon = R.drawable.bg_snow;
                break;
            case '5':
                icon = R.drawable.bg_cloudynight;
                break;
            default:
                icon = R.drawable.bg_cloudynight;
                break;
        }
        return icon;
    }

    /*
    * 天气图标
    */
    public static int getWeatherIcon(String code){
        int icon;
        switch (code){
            case "100":
                icon = R.drawable.aoo;
                break;
            case "101":
                icon = R.drawable.aoa;
                break;
            case "102":
                icon = R.drawable.aob;
                break;
            case "103":
                icon = R.drawable.aoc;
                break;
            case "104":
                icon = R.drawable.aod;
                break;
            case "200":
                icon = R.drawable.boo;
                break;
            case "201":
                icon = R.drawable.boa;
                break;
            case "202":
                icon = R.drawable.bob;
                break;
            case "203":
                icon = R.drawable.boc;
                break;
            case "204":
                icon = R.drawable.bod;
                break;
            case "205":
                icon = R.drawable.boe;
                break;
            case "206":
                icon = R.drawable.bof;
                break;
            case "207":
                icon = R.drawable.bog;
                break;
            case "208":
                icon = R.drawable.boh;
                break;
            case "209":
                icon = R.drawable.boi;
                break;
            case "210":
                icon = R.drawable.boj;
                break;
            case "211":
                icon = R.drawable.bok;
                break;
            case "212":
                icon = R.drawable.bol;
                break;
            case "213":
                icon = R.drawable.bom;
                break;
            case "300":
                icon = R.drawable.coo;
                break;
            case "301":
                icon = R.drawable.coa;
                break;
            case "302":
                icon = R.drawable.cob;
                break;
            case "303":
                icon = R.drawable.coc;
                break;
            case "304":
                icon = R.drawable.cod;
                break;
            case "305":
                icon = R.drawable.coe;
                break;
            case "306":
                icon = R.drawable.cof;
                break;
            case "307":
                icon = R.drawable.cog;
                break;
            case "308":
                icon = R.drawable.coh;
                break;
            case "309":
                icon = R.drawable.coi;
                break;
            case "310":
                icon = R.drawable.coj;
                break;
            case "311":
                icon = R.drawable.cok;
                break;
            case "312":
                icon = R.drawable.col;
                break;
            case "313":
                icon = R.drawable.com;
                break;
            case "400":
                icon = R.drawable.doo;
                break;
            case "401":
                icon = R.drawable.doa;
                break;
            case "402":
                icon = R.drawable.dob;
                break;
            case "403":
                icon = R.drawable.doc;
                break;
            case "404":
                icon = R.drawable.dod;
                break;
            case "405":
                icon = R.drawable.doe;
                break;
            case "406":
                icon = R.drawable.dof;
                break;
            case "407":
                icon = R.drawable.dog;
                break;
            case "500":
                icon = R.drawable.eoo;
                break;
            case "501":
                icon = R.drawable.eoa;
                break;
            case "502":
                icon = R.drawable.eob;
                break;
            case "503":
                icon = R.drawable.eoc;
                break;
            case "504":
                icon = R.drawable.eod;
                break;
            case "507":
                icon = R.drawable.eog;
                break;
            case "508":
                icon = R.drawable.eoh;
                break;
            case "900":
                icon = R.drawable.ioo;
                break;
            case "901":
                icon = R.drawable.ioa;
                break;
            case "999":
                icon = R.drawable.iii;
                break;
            default:
                icon = R.drawable.iii;
                break;
        }
        return icon;
    }
}

package com.yangll.bishe.happyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/1.
 */

public class WeatherOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_CITY = "create table City ("        //weather表
            + "id integer primary key autoincrement, "
            + "cityId text, "
            + "cityZh text, "
            + "provinceZh text, "
            + "lat text, "
            + "lon text)";

    public static final String CREATE_WEATHER = "create table Weather ("        //weather表
            + "id integer primary key autoincrement, "
            + "city text, "
            + "location integer, "
            + "forecast text, "
            + "now text, "
            + "suggestion text)";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CITY);
        sqLiteDatabase.execSQL(CREATE_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop if table exists City");
        sqLiteDatabase.execSQL("drop if table exists Weather");
        onCreate(sqLiteDatabase);
    }
}

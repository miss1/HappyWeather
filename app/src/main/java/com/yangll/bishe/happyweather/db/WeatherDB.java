package com.yangll.bishe.happyweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class WeatherDB {

    public static final String DB_NAME = "yll_weather";  //数据库名称

    public static final int VERSION = 1;         //数据库版本

    private static WeatherDB weatherDB;

    private SQLiteDatabase db;

    private WeatherDB (Context context){
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取WeatherDB的实例
    public synchronized static WeatherDB getInstance(Context context){
        if (weatherDB == null){
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    //将所有城市信息存储到数据库
    public void saveCities(List<City> cityList){
        if (cityList.size() > 0){
            for (City city : cityList){
                ContentValues values = new ContentValues();
                values.put("cityId", city.getId());
                values.put("cityZh", city.getCityZh());
                values.put("provinceZh", city.getProvinceZh());
                values.put("lat", city.getLat());
                values.put("lon", city.getLon());
                db.insert("City", null, values);
            }
        }
    }

    //从数据库查询城市，模糊查找
    public List<City> findCity(String key){
        List<City> cityList = new ArrayList<>();
        Cursor cursor = db.query("City", null,"cityZh like ? or provinceZh like ?",
                new String[]{"%" + key + "%", "%" + key + "%"}, null, null, null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getString(cursor.getColumnIndex("cityId")));
                city.setCityZh(cursor.getString(cursor.getColumnIndex("cityZh")));
                city.setProvinceZh(cursor.getString(cursor.getColumnIndex("provinceZh")));
                city.setLat(cursor.getString(cursor.getColumnIndex("lat")));
                city.setLon(cursor.getString(cursor.getColumnIndex("lon")));
                cityList.add(city);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return cityList;
    }

    //从数据库读取所有城市
    public List<City> loadAllCity(){
        List<City> cityList = new ArrayList<>();
        Cursor cursor = db.query("City", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getString(cursor.getColumnIndex("cityId")));
                city.setCityZh(cursor.getString(cursor.getColumnIndex("cityZh")));
                city.setProvinceZh(cursor.getString(cursor.getColumnIndex("provinceZh")));
                city.setLat(cursor.getString(cursor.getColumnIndex("lat")));
                city.setLon(cursor.getString(cursor.getColumnIndex("lon")));
                cityList.add(city);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return cityList;
    }

    //新增一个城市并插入天气信息
    public void addCity(AllResponse allResponse){
        ContentValues values = new ContentValues();
        values.put("city", allResponse.getCity());
        values.put("location", allResponse.getIsLocation());
        values.put("response", allResponse.getReponse());
        db.insert("Weather", null, values);
    }

    //查询所有城市天气信息
    public List<AllResponse> getAllResponses(){
        List<AllResponse> list = new ArrayList<>();
        Cursor cursor = db.query("Weather", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                AllResponse allResponse = new AllResponse();
                allResponse.setCity(cursor.getString(cursor.getColumnIndex("city")));
                allResponse.setIsLocation(cursor.getInt(cursor.getColumnIndex("location")));
                allResponse.setReponse(cursor.getString(cursor.getColumnIndex("response")));
                list.add(allResponse);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    //查询指定城市的天气
    public List<AllResponse> getCityResponse(String city){
        List<AllResponse> list = new ArrayList<>();
        Cursor cursor = db.query("Weather", null, "city = ?", new String[]{city}, null, null, null);
        if (cursor.moveToFirst()){
            do{
                AllResponse allResponse = new AllResponse();
                allResponse.setCity(cursor.getString(cursor.getColumnIndex("city")));
                allResponse.setIsLocation(cursor.getInt(cursor.getColumnIndex("location")));
                allResponse.setReponse(cursor.getString(cursor.getColumnIndex("response")));
                list.add(allResponse);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    //查询定位城市的天气
    public List<AllResponse> getLocationCityResponse(int location){
        List<AllResponse> list = new ArrayList<>();
        Cursor cursor = db.query("Weather", null, "location = ?", new String[]{String.valueOf(location)}, null, null, null);
        if (cursor.moveToFirst()){
            do{
                AllResponse allResponse = new AllResponse();
                allResponse.setCity(cursor.getString(cursor.getColumnIndex("city")));
                allResponse.setIsLocation(cursor.getInt(cursor.getColumnIndex("location")));
                allResponse.setReponse(cursor.getString(cursor.getColumnIndex("response")));
                list.add(allResponse);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    //删除指定城市的数据
    public void deleteCityResponse(String city){
        db.delete("Weather", "city = ?", new String[]{city});
    }

    //更新指定城市的天气信息
    public void  updateWeatherResponse(String city, String response){
        ContentValues contentValues = new ContentValues();
        contentValues.put("response", response);
        db.update("Weather", contentValues, "city = ?", new String[]{city});
    }

    //重新设置定位城市
    public void updateLocation(String city){
        clearLocation();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("location", 1);
        db.update("Weather", contentValues1, "city = ?", new String[]{city});
    }

    //清除位置信息
    public void clearLocation(){
        for (AllResponse allResponse : getAllResponses()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("location", 0);
            db.update("Weather", contentValues, "city = ?", new String[]{allResponse.getCity()});
        }
    }
}

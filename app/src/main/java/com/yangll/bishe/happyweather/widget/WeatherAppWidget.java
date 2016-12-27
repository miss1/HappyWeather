package com.yangll.bishe.happyweather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.activity.MainActivity;
import com.yangll.bishe.happyweather.bean.Weather;
import com.yangll.bishe.happyweather.bean.WeatherJson;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.http.WeatherUtil;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        WeatherDB weatherDB = WeatherDB.getInstance(context);
        Gson gson = new Gson();
        WeatherJson weatherJson = gson.fromJson(weatherDB.getLocationCityResponse(1).get(0).getReponse(), WeatherJson.class);
        Weather weather = weatherJson.getHeWeather5().get(0);
        // 填充数据
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
        views.setTextViewText(R.id.widget_time, weather.getDaily_forecast().get(0).getDate());
        views.setTextViewText(R.id.widget_tmp, weather.getDaily_forecast().get(0).getTmp().getMin()+"° - "+weather.getDaily_forecast().get(0).getTmp().getMax()+"°");
        views.setTextViewText(R.id.widget_txt, weather.getDaily_forecast().get(0).getCond().getTxt_d());
        views.setImageViewResource(R.id.widget_icon, WeatherUtil.getWeatherIcon(weather.getDaily_forecast().get(0).getCond().getCode_d()));
        views.setTextViewText(R.id.widget_city, weather.getBasic().getCity());
        //点击事件
        views.setOnClickPendingIntent(R.id.layout, getPendingIntent(context));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    //跳转到主界面
    private static PendingIntent getPendingIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return pendingIntent;
    }

    //接受广播，根据接收到的广播做出相应处理
    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
        if (intent.getAction().equals(JSONCon.BRODCAST_UPDATE)){
            WeatherDB weatherDB = WeatherDB.getInstance(context);
            Gson gson = new Gson();
            WeatherJson weatherJson = gson.fromJson(weatherDB.getLocationCityResponse(1).get(0).getReponse(), WeatherJson.class);
            Weather weather = weatherJson.getHeWeather5().get(0);
            views.setTextViewText(R.id.widget_time, weather.getDaily_forecast().get(0).getDate());
            views.setTextViewText(R.id.widget_tmp, weather.getDaily_forecast().get(0).getTmp().getMin()+"° - "+weather.getDaily_forecast().get(0).getTmp().getMax()+"°");
            views.setTextViewText(R.id.widget_txt, weather.getDaily_forecast().get(0).getCond().getTxt_d());
            views.setImageViewResource(R.id.widget_icon, WeatherUtil.getWeatherIcon(weather.getDaily_forecast().get(0).getCond().getCode_d()));
            views.setTextViewText(R.id.widget_city, weather.getBasic().getCity());
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, WeatherAppWidget.class);
        appWidgetManager.updateAppWidget(componentName, views);
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


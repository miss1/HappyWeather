package com.yangll.bishe.happyweather.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yangll.bishe.happyweather.R;

/**
 * Created by Administrator on 2017/3/15.
 */

public class FourPartDialog {

    private Context context;
    private Dialog dialog;
    private RelativeLayout fpLayout_bg;
    private ImageView weatherpk;
    private ImageView weatherline;
    private ImageView weathergame;
    private ImageView historytoday;
    private ImageView close;
    private Display display;

    public FourPartDialog(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public FourPartDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_fourpartdialog, null);

        fpLayout_bg = (RelativeLayout) view.findViewById(R.id.fpLayout_bg);
        weatherpk = (ImageView) view.findViewById(R.id.weatherpk);
        weatherline = (ImageView) view.findViewById(R.id.weatherline);
        weathergame = (ImageView) view.findViewById(R.id.weathergame);
        historytoday = (ImageView) view.findViewById(R.id.historytoday);
        close = (ImageView) view.findViewById(R.id.close);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog布局大小
        fpLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), (int) (display.getWidth() * 0.85)));
        return this;
    }

    //绑定点击事件
    public FourPartDialog setWeatherPKBtn(final View.OnClickListener listener){
        weatherpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        return this;
    }

    public FourPartDialog setWeatherLineBtn(final View.OnClickListener listener){
        weatherline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        return this;
    }

    public FourPartDialog setWeatherGameBtn(final View.OnClickListener listener){
        weathergame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        return this;
    }

    public FourPartDialog setHistoryTodayBtn(final View.OnClickListener listener){
        historytoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        return this;
    }

    public FourPartDialog setCloseBtn(final View.OnClickListener listener){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show(){
        dialog.show();
    }
}

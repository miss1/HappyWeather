package com.yangll.bishe.happyweather.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.activity.WeatherPkActivity;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.adapter.SearchCityAdapter;
import com.yangll.bishe.happyweather.bean.City;
import com.yangll.bishe.happyweather.db.WeatherDB;
import com.yangll.bishe.happyweather.http.SelectCityCallbackListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class SelectCityDialog {

    private Context context;
    private Dialog dialog;
    private Display display;
    private RelativeLayout seclect_dialog;
    private EditText input;
    private RecyclerView citylist;
    private Button btn_ok;
    private ImageView close;

    private SearchCityAdapter adapter;
    private List<City> cities = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private WeatherDB weatherDB;
    private SelectCityCallbackListener cityCallbackListener;

    public SelectCityDialog(Context context, SelectCityCallbackListener cityCallbackListener){
        this.context = context;
        this.cityCallbackListener = cityCallbackListener;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public SelectCityDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_selectcitydialog, null);
        seclect_dialog = (RelativeLayout) view.findViewById(R.id.seclect_dialog);
        input = (EditText) view.findViewById(R.id.input);
        citylist = (RecyclerView) view.findViewById(R.id.citylist);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        close = (ImageView) view.findViewById(R.id.close);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog布局大小
        seclect_dialog.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.7), (int) (display.getWidth() * 0.9)));
        return this;
    }

    //展示城市列表信息
    public SelectCityDialog initData(){
        weatherDB = WeatherDB.getInstance(context);
        adapter = new SearchCityAdapter("gray");
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        citylist.setLayoutManager(linearLayoutManager);
        citylist.setAdapter(adapter);

        cities = weatherDB.loadAllCity();
        adapter.bindDatas(cities);
        adapter.notifyDataSetChanged();

        initItemClick();
        initEdittxetChange();
        initclick();
        return this;
    }

    //搜索框监听
    private void initEdittxetChange(){
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = input.getText().toString();
                cities = weatherDB.findCity(name);
                adapter.bindDatas(cities);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //adapter点击监听
    private void initItemClick(){
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                input.setText(cities.get(position).getCityZh());
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }

    //判断某个城市在不在城市列表里
    private boolean isInlists(String name){
        for (int i = 0; i < cities.size(); i++){
            if (name.equals(cities.get(i).getCityZh())){
                return true;
            }
        }
        return false;
    }

    private void initclick(){
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(input.getText().toString())){
                    Toast.makeText(context, "城市名不能为空", Toast.LENGTH_SHORT).show();
                }else if (!isInlists(input.getText().toString())){
                    Toast.makeText(context, "该城市不存在", Toast.LENGTH_SHORT).show();
                }else {
                    cityCallbackListener.onFinish(input.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void show(){
        dialog.show();
    }
}

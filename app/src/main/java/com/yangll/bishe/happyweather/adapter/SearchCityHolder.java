package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.City;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SearchCityHolder extends BaseViewHolder {

    @BindView(R.id.cityname)
    TextView cityname;

    @BindView(R.id.cityline)
    TextView cityline;

    @BindView(R.id.cityprovince)
    TextView cityprovince;

    private String txtcolor;

    public SearchCityHolder(Context context, ViewGroup root, OnRecyclerViewListener listener, String txtcolor) {
        super(context, root, R.layout.item_searchcity, listener);
        this.txtcolor = txtcolor;
    }

    @Override
    public void bindData(Object o) {
        City city = (City) o;
        cityname.setText(city.getCityZh());
        cityline.setText("--");
        cityprovince.setText(city.getProvinceZh());
        if (txtcolor.equals("white")){
            cityname.setTextColor(Color.WHITE);
            cityline.setTextColor(Color.WHITE);
            cityprovince.setTextColor(Color.WHITE);
        }else {
            cityname.setTextColor(Color.GRAY);
            cityline.setTextColor(Color.GRAY);
            cityprovince.setTextColor(Color.GRAY);
        }
    }
}

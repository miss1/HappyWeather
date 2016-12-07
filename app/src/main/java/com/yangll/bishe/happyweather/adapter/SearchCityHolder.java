package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
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

    public SearchCityHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_searchcity, listener);
    }

    @Override
    public void bindData(Object o) {
        City city = (City) o;
        cityname.setText(city.getCityZh());
        cityline.setText("--");
        cityprovince.setText(city.getProvinceZh());
    }
}

package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.AllResponse;
import com.yangll.bishe.happyweather.bean.Weather;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/12/11.
 */

public class SpinerListHolder extends BaseViewHolder {

    @BindView(R.id.spinnerItem)
    TextView spinerItem;

    public SpinerListHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_spaner, listener);
    }

    @Override
    public void bindData(Object o) {
        AllResponse allResponse = (AllResponse) o;
        spinerItem.setText(allResponse.getCity());
    }
}

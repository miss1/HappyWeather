package com.yangll.bishe.happyweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class ManagerCityHolder2 extends RecyclerView.ViewHolder{

    public LinearLayout item_layout;

    public TextView item_name;

    public ImageView item_location;

    public ImageView item_icon;

    public TextView item_tmp;

    public TextView item_delete;

    public ManagerCityHolder2(View view) {
        super(view);
        item_layout = (LinearLayout) view.findViewById(R.id.item_layout);
        item_name = (TextView) view.findViewById(R.id.item_name);
        item_icon = (ImageView) view.findViewById(R.id.item_icon);
        item_location = (ImageView) view.findViewById(R.id.item_location);
        item_tmp = (TextView) view.findViewById(R.id.item_tmp);
        item_delete = (TextView) view.findViewById(R.id.item_delete);
    }
}

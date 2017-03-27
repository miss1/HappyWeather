package com.yangll.bishe.happyweather.adapter;

import android.view.View;

/**
 * Created by Administrator on 2017/3/27.
 */

public interface OnItemRemoveListener {

    void onItemClick(View view, int position);
    void onDeleteClick(int position);
}

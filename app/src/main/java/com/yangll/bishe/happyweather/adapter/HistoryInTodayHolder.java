package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.HistoryListResult;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/15.
 */

public class HistoryInTodayHolder extends BaseViewHolder {

    @BindView(R.id.h_item_bg)
    RelativeLayout h_item_bg;

    @BindView(R.id.h_item_title)
    TextView h_item_title;

    @BindView(R.id.h_item_content)
    TextView h_item_content;

    private int[] bg = {R.drawable.hbg1, R.drawable.hbg2, R.drawable.hbg3, R.drawable.hbg4};

    public HistoryInTodayHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_historyintoday, listener);
    }

    @Override
    public void bindData(Object o) {
        ViewGroup.LayoutParams lp = h_item_bg.getLayoutParams();
        lp.height = (int) (210 + Math.random() * 250);

        h_item_bg.setBackgroundResource(bg[(int)(Math.random()*4)]);

        HistoryListResult result = (HistoryListResult) o;
        h_item_title.setText(result.getDate());
        h_item_content.setText(result.getTitle());
    }
}

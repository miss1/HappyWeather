package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.bean.puzzle;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GameHolder extends BaseViewHolder {
    @BindView(R.id.game_itemimg)
    ImageView game_itemimg;

    @BindView(R.id.game_item)
    RelativeLayout game_item;

    private Context context;

    public GameHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_game, listener);
        this.context = context;
    }

    @Override
    public void bindData(Object o) {
        puzzle p = (puzzle) o;
        Glide.with(context).load(p.getImg()).placeholder(R.drawable.loadtip).error(R.drawable.loadtip).into(game_itemimg);

        //设置选中状态
        if (p.isSelect()){
            game_item.setBackgroundColor(Color.parseColor("#7f0080ff"));
        }else {
            game_item.setBackgroundColor(0);
        }
    }

}

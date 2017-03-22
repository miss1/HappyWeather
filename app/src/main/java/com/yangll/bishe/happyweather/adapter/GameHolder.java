package com.yangll.bishe.happyweather.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yangll.bishe.happyweather.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GameHolder extends BaseViewHolder {
    @BindView(R.id.game_itemimg)
    ImageView game_itemimg;

    public GameHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_game, listener);
    }

    @Override
    public void bindData(Object o) {
        Bitmap bmp = (Bitmap) o;
        game_itemimg.setImageBitmap(bmp);
    }

}

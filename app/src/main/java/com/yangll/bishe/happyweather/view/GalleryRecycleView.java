package com.yangll.bishe.happyweather.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/21.
 */

public class GalleryRecycleView extends RecyclerView {

    public GalleryRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private View mCurrentView;

    //滚动时回调的接口
    private OnItemScrollChangeListener onItemScrollChangeListener;

    public void setOnItemScrollChangeListener(OnItemScrollChangeListener onItemScrollChangeListener){
        this.onItemScrollChangeListener = onItemScrollChangeListener;
    }

    public interface OnItemScrollChangeListener{
        void onChange(View view, int position);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mCurrentView = getChildAt(0);

        if (onItemScrollChangeListener != null){
            onItemScrollChangeListener.onChange(mCurrentView, getChildAdapterPosition(mCurrentView));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_MOVE){
            mCurrentView = getChildAt(0);
            if (onItemScrollChangeListener != null){
                onItemScrollChangeListener.onChange(mCurrentView, getChildAdapterPosition(mCurrentView));
            }
        }
        return super.onTouchEvent(e);
    }
}

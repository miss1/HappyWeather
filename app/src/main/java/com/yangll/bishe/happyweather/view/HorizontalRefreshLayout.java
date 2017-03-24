package com.yangll.bishe.happyweather.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.GameAdapter;
import com.yangll.bishe.happyweather.http.OnLoadListener;

/**
 * Created by Administrator on 2017/3/24.
 */

public class HorizontalRefreshLayout extends SwipeRefreshLayout {

    /**
     * 滑动到最右面时的左拉操作
     */

    private int mTouchSlop;

    /**
     * RecycleView实例
     */

    private RecyclerView mRecycleView;

    /**
     * 左拉监听器，到了最右边的左拉加载操作
     */

    private OnLoadListener onLoadListener;

    /**
     * RecyclerView的加载中footer
     */

    private View mRecycleViewFooter;

    /**
     * 按下时的y坐标
     */

    private int mXDown;

    /**
     * 抬起时的y坐标，与mYDown一起用于滑动到底部时判断是左拉还是右拉
     */

    private int mLastX;

    /**
     * 是否在加载中（左拉加载更多）
     */

    private boolean isLoading = false;

    private GameAdapter adapter;

    public HorizontalRefreshLayout(Context context) {
        super(context);
    }

    public HorizontalRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mRecycleViewFooter = LayoutInflater.from(context).inflate(R.layout.recycleview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mRecycleView == null){
            getRecycleView();
        }
    }

    //获取RecycleView对象
    private void getRecycleView(){
        int chailds = getChildCount();
        if (chailds > 0){
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView){
                mRecycleView = (RecyclerView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (canLoad()){
                            loaddata();
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action){
            //按下
            case MotionEvent.ACTION_DOWN:
                mXDown = (int) ev.getRawX();
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                mLastX = (int) ev.getRawX();
                break;
            //抬起
            case MotionEvent.ACTION_UP:
                if (canLoad()){
                    loaddata();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, RecycleView不在加载中, 且为左拉操作.
     */
    private boolean canLoad(){
        return isBottom() && !isLoading && isPullLeft();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom(){
        if (mRecycleView != null && mRecycleView.getAdapter() != null){
            if (mRecycleView.getLayoutManager() instanceof LinearLayoutManager){
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecycleView.getLayoutManager();
                return linearLayoutManager.findLastVisibleItemPosition() == mRecycleView.getAdapter().getItemCount() - 1;
            }
        }
        return false;
    }

    /**
     * 是否是左拉操作
     */
    private boolean isPullLeft(){
        return (mXDown - mLastX) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loaddata(){
        if (onLoadListener != null){
            setLoading(true);
            onLoadListener.onLoad();
        }
    }

    public void setLoading(boolean loading){
        isLoading = loading;
        if (isLoading){
            adapter.setFooterView(mRecycleViewFooter);
        }else {
            adapter.setFooterView(null);
            mXDown = 0;
            mLastX = 0;
        }
    }

    public void setOnLoadListener (OnLoadListener onLoadListener){
        this.onLoadListener = onLoadListener;
    }

    public void setAdapter(GameAdapter adapter){
        this.adapter = adapter;
    }
}

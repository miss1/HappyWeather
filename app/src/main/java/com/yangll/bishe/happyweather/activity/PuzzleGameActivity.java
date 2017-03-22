package com.yangll.bishe.happyweather.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.GameAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.bean.puzzle;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.gameview.GamePintuLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PuzzleGameActivity extends AppCompatActivity {

    @BindView(R.id.activity_puzzle_game)
    RelativeLayout activity_puzzle_game;

    @BindView(R.id.game_loading)
    ImageView game_loading;

    @BindView(R.id.game_chooselist)
    RecyclerView game_chooselist;

    @BindView(R.id.game_view)
    GamePintuLayout game_view;

    @BindView(R.id.game_begin)
    Button game_begin;

    @BindView(R.id.game_level)
    Button game_level;

    @BindView(R.id.game_count)
    Button game_count;

    @BindView(R.id.game_show)
    Button game_show;

    private GameAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<Bitmap> bitmapList = new ArrayList<>();

    private int count = 0;
    private int limit = 7;
    private int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_game);
        ButterKnife.bind(this);

        if (!"".equals(WeatherUtil.bg)){
            activity_puzzle_game.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        //设置加载动画
        Animation animiation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator(); //设置动画匀速运动
        animiation.setInterpolator(lin);
        game_loading.startAnimation(animiation);

        adapter = new GameAdapter();
        linearLayoutManager = new LinearLayoutManager(PuzzleGameActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        game_chooselist.setLayoutManager(linearLayoutManager);
        game_chooselist.setAdapter(adapter);

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        //qurtyImgFile();
    }

    //从服务器查询图片文件
    private void qurtyImgFile(){
        BmobQuery<puzzle> query = new BmobQuery<>();
        query.setSkip(count);
        query.setLimit(limit);
        query.findObjects(new FindListener<puzzle>() {
            @Override
            public void done(List<puzzle> list, BmobException e) {
                if (e == null){
                    if (list.size() != 0){
                        loadImg(list);
                        count += limit;
                    }
                }else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    //下载图片并显示
    private void loadImg(List<puzzle> lists){
        length += lists.size();
        for (int i = 0; i < lists.size(); i++){
            new HttpPost(lists.get(i).getImg().getFileUrl(), loadImgHandler).imgexe();
        }
        Log.e("url", lists.get(0).getImg().getUrl());
    }

    private Handler loadImgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Bitmap bmp = (Bitmap) msg.obj;
                    bitmapList.add(bmp);
                    Log.e("map", bitmapList.size()+"");
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(PuzzleGameActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //开始游戏
    @OnClick(R.id.game_begin)
    public void gameBegin(View view){
        game_view.startGame();
    }

    //选择难度
    @OnClick(R.id.game_level)
    public void gameLevel(View view){
        game_view.nextLevel();
    }

    //返回上一页面
    @OnClick(R.id.game_back)
    public void gameback(View view){
        finish();
    }
}

package com.yangll.bishe.happyweather.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.GameAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.bean.knowledge;
import com.yangll.bishe.happyweather.bean.puzzle;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.AlertDialog;
import com.yangll.bishe.happyweather.view.MyProgressBar;
import com.yangll.bishe.happyweather.view.gameview.GamePintuLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @BindView(R.id.game_success)
    RelativeLayout game_success;

    @BindView(R.id.game_success_tips)
    TextView game_success_tips;

    private GameAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<puzzle> urlLists = new ArrayList<>();

    private ProgressBar progressBar;

    private int count = 0;
    private int limit = 7;
    private int length = 0;

    private Bitmap prisentBitmap;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_game);
        ButterKnife.bind(this);

        if (!"".equals(WeatherUtil.bg)){
            activity_puzzle_game.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        sharedPreferences = this.getSharedPreferences("puzzle", MODE_PRIVATE);

        //设置加载动画
        Animation animiation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator(); //设置动画匀速运动
        animiation.setInterpolator(lin);
        game_loading.startAnimation(animiation);

        MyProgressBar myProgressBar = new MyProgressBar();
        progressBar = myProgressBar.createMyProgressBar(this, null);

        adapter = new GameAdapter();
        linearLayoutManager = new LinearLayoutManager(PuzzleGameActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        game_chooselist.setLayoutManager(linearLayoutManager);
        game_chooselist.setAdapter(adapter);

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                changeImg(position);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        qurtyImgFile();

        IntentFilter filter = new IntentFilter(JSONCon.PUZZLE_SUCCESS);
        registerReceiver(broadcastReceiver, filter);
    }

    //点击图片item，切换图片重新开始游戏
    private void changeImg(final int position){
        if (game_begin.getText().equals("开始")){
            initSelection(position);
            adapter.bindDatas(urlLists);
            adapter.notifyDataSetChanged();
            loadImg(position);
        }else {
            new AlertDialog(PuzzleGameActivity.this).builder().setTitle("切换图片")
                    .setMsg("游戏正在进行，确定要切换图片重新开始吗？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            initSelection(position);
                            adapter.bindDatas(urlLists);
                            adapter.notifyDataSetChanged();
                            loadImg(position);
                            game_begin.setText("开始");
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }

    }

    //下载图片
    private void loadImg(final int position){
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = Glide.with(PuzzleGameActivity.this).load(urlLists.get(position).getImg())
                            .asBitmap().centerCrop().into(300, 300).get();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = bitmap;
                    imgHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }).start();
    }

    //下载成功之后重置游戏的图片
    private Handler imgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                Bitmap bitmap = (Bitmap) msg.obj;
                if (bitmap != null){
                    game_view.setBitmap(bitmap);
                    prisentBitmap = bitmap;
                    Log.e("bitmap", new BitmapDrawable(prisentBitmap).toString());
                    if (sharedPreferences.getBoolean(prisentBitmap.toString(), false)){
                        game_show.setClickable(true);
                        game_show.setBackgroundResource(R.drawable.a_selector_blue_button);
                    }else {
                        game_show.setClickable(false);
                        game_show.setBackgroundResource(0);
                    }
                }
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

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
                        urlLists.addAll(list);
                        if (count == 0){
                            loadImg(0);
                            initSelection(0);
                        }
                        adapter.bindDatas(urlLists);
                        adapter.notifyDataSetChanged();
                        count += limit;
                    }
                }else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    //设置选中状态
    private void initSelection(int position){
        for (puzzle pu : urlLists){
            pu.setSelect(false);
        }
        urlLists.get(position).setSelect(true);
    }

    //开始游戏
    @OnClick(R.id.game_begin)
    public void gameBegin(View view){
        if (game_begin.getText().equals("开始")){
            game_view.startGame();
            game_begin.setText("复原");
        }else {
            new AlertDialog(PuzzleGameActivity.this).builder().setTitle("复原游戏")
                    .setMsg("游戏正在进行，确定要复原吗？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            game_view.reSetGame();
                            game_begin.setText("开始");
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }

    }

    //选择难度
    @OnClick(R.id.game_level)
    public void gameLevel(View view){
        if (game_begin.getText().equals("开始")){
            game_view.nextLevel(game_level);
        }else {
            new AlertDialog(PuzzleGameActivity.this).builder().setTitle("重新开始")
                    .setMsg("游戏尚未结束，确定要切换难度重新开始吗？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            game_view.nextLevel(game_level);
                            game_begin.setText("开始");
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }
    }

    //查看成功后的结果
    @OnClick(R.id.game_show)
    public void gameShow(View view){
        if (game_show.getText().equals("show")){
            game_view.setVisibility(View.GONE);
            game_success.setVisibility(View.VISIBLE);
            game_begin.setClickable(false);
            game_level.setClickable(false);
            game_show.setText("back");
            game_success.setBackground(new BitmapDrawable(prisentBitmap));
            game_success_tips.setText(sharedPreferences.getString(prisentBitmap.toString(), ""));
        }else {
            game_show.setText("show");
            game_view.setVisibility(View.VISIBLE);
            game_success.setVisibility(View.GONE);
            game_begin.setClickable(true);
            game_level.setClickable(true);
        }
    }

    //返回上一页面
    @OnClick(R.id.game_back)
    public void gameback(View view){
        finish();
    }

    //成功完成游戏之后的处理
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!sharedPreferences.getBoolean(prisentBitmap.toString(), false)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(prisentBitmap.toString(), true);
                editor.commit();
            }
            queryKnowledge();
            game_show.setClickable(true);
            game_show.setBackgroundResource(R.drawable.a_selector_blue_button);

            game_view.reSetGame();
            game_begin.setText("开始");
            Toast.makeText(PuzzleGameActivity.this, "Wow, Success", Toast.LENGTH_LONG).show();
        }
    };

    //从服务器中获取气象小知识
    private void queryKnowledge(){
        BmobQuery<knowledge> query = new BmobQuery<>();
        int i = (int) (Math.random() * 15);
        query.addWhereEqualTo("sing", i+"");
        query.findObjects(new FindListener<knowledge>() {
            @Override
            public void done(List<knowledge> list, BmobException e) {
                if (e == null){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(prisentBitmap.toString(), list.get(0).getContent());
                    editor.commit();
                    Log.e("tips:", list.get(0).getContent());
                    Log.e("bitmap1", new BitmapDrawable(prisentBitmap).toString());
                }else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}

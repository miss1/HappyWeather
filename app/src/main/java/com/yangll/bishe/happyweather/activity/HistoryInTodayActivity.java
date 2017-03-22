package com.yangll.bishe.happyweather.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yangll.bishe.happyweather.R;
import com.yangll.bishe.happyweather.adapter.HistoryInTodayAdapter;
import com.yangll.bishe.happyweather.adapter.OnRecyclerViewListener;
import com.yangll.bishe.happyweather.bean.HistoryDetail;
import com.yangll.bishe.happyweather.bean.HistoryDetailResult;
import com.yangll.bishe.happyweather.bean.HistoryList;
import com.yangll.bishe.happyweather.bean.HistoryListResult;
import com.yangll.bishe.happyweather.http.HttpPost;
import com.yangll.bishe.happyweather.http.JSONCon;
import com.yangll.bishe.happyweather.http.WeatherUtil;
import com.yangll.bishe.happyweather.view.MyProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryInTodayActivity extends AppCompatActivity {

    @BindView(R.id.activity_history_in_today)
    LinearLayout activity_history_in_today;

    @BindView(R.id.history_back)
    ImageView history_back;

    @BindView(R.id.history_date)
    TextView history_date;

    @BindView(R.id.history_list)
    RecyclerView history_list;

    @BindView(R.id.history_detailpage)
    LinearLayout history_detailpage;

    @BindView(R.id.h_detail_time)
    TextView h_detail_time;

    @BindView(R.id.h_detail_title)
    TextView h_detail_title;

    @BindView(R.id.h_detail_img)
    ImageView h_detail_img;

    @BindView(R.id.h_detail_text)
    TextView h_detail_text;

    private ProgressBar progressBar;

    private List<HistoryListResult> lists = new ArrayList<>();
    private HistoryInTodayAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private List<HistoryDetailResult> detailResults = new ArrayList<>();
    private int detaillocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_in_today);
        ButterKnife.bind(this);

        if (!WeatherUtil.bg.equals("")){
            activity_history_in_today.setBackgroundResource(WeatherUtil.getWeatherBg(WeatherUtil.bg));
        }

        //获取当前的时间
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        history_date.setText(month + "-" + day);

        MyProgressBar myProgressBar = new MyProgressBar();
        progressBar = myProgressBar.createMyProgressBar(this,null);

        adapter = new HistoryInTodayAdapter();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        history_list.setLayoutManager(staggeredGridLayoutManager);
        history_list.setAdapter(adapter);

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                detaillocation = position;
                queryDetail(lists.get(position).getE_id());
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        queryEvent(month, day);
    }

    //返回按钮事件监听
    @OnClick(R.id.history_back)
    public void back(View view){
        if (history_list.getVisibility() == View.VISIBLE){
            finish();
        }else {
            history_list.setVisibility(View.VISIBLE);
            history_detailpage.setVisibility(View.GONE);
        }
    }

    //从服务器接口查询历史上的今天发生的所有事件并显示到列表中
    private void queryEvent(int month, int day){
        progressBar.setVisibility(View.VISIBLE);
        new HttpPost(JSONCon.H_SERVER_URL + "?key=" + JSONCon.H_KEY + "&date=" + month + "/" + day, queryEventHandle).exe();
    }

    private Handler queryEventHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Log.e("Tag", (String) msg.obj);
                    Gson gson = new Gson();
                    String obj = (String) msg.obj;
                    HistoryList historyList = gson.fromJson(obj, HistoryList.class);
                    lists = historyList.getResult();
                    adapter.bindDatas(lists);
                    adapter.notifyDataSetChanged();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(HistoryInTodayActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

    //从服务器查询单条事件的详细信息
    private void queryDetail(String id){
        progressBar.setVisibility(View.VISIBLE);
        new HttpPost(JSONCon.H_DETAIL_SERVER_URL + "?key=" + JSONCon.H_KEY + "&e_id=" + id, queryDetailHandler).exe();
    }

    private Handler queryDetailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Log.e("Tag", (String) msg.obj);
                    Gson gson = new Gson();
                    String obj = (String) msg.obj;
                    HistoryDetail historyDetail = gson.fromJson(obj, HistoryDetail.class);
                    detailResults = historyDetail.getResult();
                    updataPageDetail();
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(HistoryInTodayActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

    //更新详细页面的信息
    private void updataPageDetail(){
        history_list.setVisibility(View.GONE);
        history_detailpage.setVisibility(View.VISIBLE);
        h_detail_time.setText(lists.get(detaillocation).getDate());
        h_detail_title.setText(lists.get(detaillocation).getTitle());

        if (detailResults != null){
            h_detail_text.setText(detailResults.get(0).getContent());
            if (!detailResults.get(0).getPicNo().equals("0")){
                new HttpPost(detailResults.get(0).getPicUrl().get(0).getUrl(), loadImgHandler).imgexe();
            }else {
                h_detail_img.setImageBitmap(null);
                h_detail_img.setBackgroundResource(R.drawable.zwcptp);
            }
        }else {
            h_detail_text.setText("暂无详细信息");
            h_detail_img.setImageBitmap(null);
            h_detail_img.setBackgroundResource(R.drawable.zwcptp);
        }
    }

    private Handler loadImgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpPost.POST_SUCCES:
                    Bitmap bmp = (Bitmap) msg.obj;
                    h_detail_img.setImageBitmap(bmp);
                    break;
                case HttpPost.POST_LOGIC_ERROR:
                    Toast.makeText(HistoryInTodayActivity.this, (String) msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}

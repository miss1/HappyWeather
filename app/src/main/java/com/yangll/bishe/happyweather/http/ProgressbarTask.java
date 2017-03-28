package com.yangll.bishe.happyweather.http;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangll.bishe.happyweather.bean.knowledge;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ProgressbarTask extends AsyncTask<Void, Integer, Void> {
    private ProgressBar barL;
    private ProgressBar barR;
    private int valueL;
    private int valueR;
    private TextView txtL;
    private TextView txtR;
    private int width;
    private RelativeLayout valueRe;
    private TextView pk_conclosion;

    /**
     * @param barL  左边的进度条
     * @param barR  右边的进度条
     * @param valueL  左边进度条的最大示数
     * @param txtL  左边进度条的示数
     * @param txtR  右边进度条的示数
     * @param width 进度条的总长度
     */
    public ProgressbarTask(ProgressBar barL, ProgressBar barR, int valueL, TextView txtL, TextView txtR, int width, RelativeLayout valueRe){
        this.barL = barL;
        this.barR = barR;
        this.valueL = valueL;
        this.valueR = 100 - valueL;
        this.txtL = txtL;
        this.txtR = txtR;
        this.width = width;
        this.valueRe = valueRe;
    }

    public ProgressbarTask(ProgressBar barL, ProgressBar barR, int valueL, TextView txtL, TextView txtR, int width, RelativeLayout valueRe, TextView pk_conclosion){
        this.barL = barL;
        this.barR = barR;
        this.valueL = valueL;
        this.valueR = 100 - valueL;
        this.txtL = txtL;
        this.txtR = txtR;
        this.width = width;
        this.valueRe = valueRe;
        this.pk_conclosion = pk_conclosion;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                Integer valL = msg.arg1;
                Integer varR = msg.arg2;

                //设置进度条的数字
                txtL.setText(valL+"");
                txtR.setText(varR+"");

                //设置数字的位置（随进度条变化）
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) txtL.getLayoutParams();
                lp.leftMargin = width*valL/100 - 25;
                txtL.setLayoutParams(lp);

                RelativeLayout.LayoutParams lpr = (RelativeLayout.LayoutParams) txtR.getLayoutParams();
                lpr.rightMargin = width*varR/100 - 25;
                txtR.setLayoutParams(lpr);

                //进度条滚动结束之后的操作
                if (valL + varR == 100){
                    valueRe.setVisibility(View.VISIBLE);
                    if (pk_conclosion != null){
                        if (WeatherUtil.list.size() == 0){
                            queryKnowledge();
                        }else {
                            Log.e("knowledgeSize", WeatherUtil.list.size()+"");
                            int i = (int) (Math.random() * (WeatherUtil.list.size() - 1));
                            pk_conclosion.setText(WeatherUtil.list.get(i).getContent());
                            pk_conclosion.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }
            super.handleMessage(msg);
        }
    };

    //从服务器中获取气象小知识
    private void queryKnowledge(){
        BmobQuery<knowledge> query = new BmobQuery<>();
        //int i = (int) (Math.random() * 15);
        //query.addWhereEqualTo("sing", i+"");
        query.findObjects(new FindListener<knowledge>() {
            @Override
            public void done(List<knowledge> list, BmobException e) {
                if (e == null){
                    WeatherUtil.list = list;
                    int i = (int) (Math.random() * (list.size() - 1));
                    pk_conclosion.setText(list.get(i).getContent());
                    pk_conclosion.setVisibility(View.VISIBLE);
                }else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //滚动进度条
        barL.setProgress(values[0]);
        barR.setProgress(values[1]);

        Message msg = new Message();
        msg.what = 1;
        msg.arg1 = values[0];
        msg.arg2 = values[1];
        handler.sendMessage(msg);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int i = 0;
        int j = 0;
        while (i + j < 100){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i < valueL){
                i++;
            }
            if (j < valueR){
                j++;
            }
            publishProgress(i, j);
        }
        return null;
    }
}

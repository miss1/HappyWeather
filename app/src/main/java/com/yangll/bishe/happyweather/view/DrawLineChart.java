package com.yangll.bishe.happyweather.view;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2017/3/16.
 */

public class DrawLineChart {

    private LineChartView lineChartView;
    private String[] date;
    private int[] score;
    private String[] score1;
    private List<PointValue> mPointValues = new ArrayList<>();
    private List<AxisValue> mAxisValues = new ArrayList<>();

    private int[] dealTimedate = new int[7];

    private boolean istmp = false;      //标记是画温度的折线还是能见度的

    public DrawLineChart(LineChartView lineChartView, String[] date, int[] score, boolean istmp){
        this.lineChartView = lineChartView;
        this.date = date;
        this.score = score;
        this.istmp = istmp;
        getAxisXLables();
        getAxisPoints();
        initLineChart();
    }

    public DrawLineChart(LineChartView lineChartView, String[] date, String[] score1){
        this.lineChartView = lineChartView;
        this.date = date;
        this.score1 = score1;
        getAxisXLables();
        getAcisPoints1();
        initLineChart();
    }

    //设置x轴的显示
    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++){
            mAxisValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    //图表的每个点的显示
    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++){
            PointValue value = new PointValue(i, score[i]);
            if (istmp){
                value.setLabel(score[i] + "°");
            }else {
                value.setLabel(score[i] + "km");
            }
            mPointValues.add(value);
        }
    }

    //当数据集为String类型时，图表每个点的显示
    private void getAcisPoints1(){
        dealTimedate();
        for (int i = 0; i < score1.length; i++){
            PointValue value = new PointValue(i, dealTimedate[i]);
            value.setLabel(score1[i]);
            mPointValues.add(value);
        }
    }

    //当数据集为String类型时，将其转化为int。处理时间数据（七个数据中同时存在6:59和7:01这样的情况时)
    private void dealTimedate(){
        int max = timetoInt(score1[0]); int min = timetoInt(score1[0]);
        for (int i = 0; i < score1.length; i++){
            if (timetoInt(score1[i]) > max){
                max = timetoInt(score1[i]);
            }
            if (timetoInt(score1[i]) < min){
                min = timetoInt(score1[i]);
            }
        }

        int maxh = max/100;
        int minh = min/100;
        if (maxh != minh){
            for (int i = 0; i < score1.length; i++){
                int a = timetoInt(score1[i])/100;
                if (a == maxh){
                    dealTimedate[i] = timetoInt(score1[i])%100 + 60;
                }else {
                    dealTimedate[i] = timetoInt(score1[i])%100;
                }
            }
        }else {
            for (int i = 0; i < score1.length; i++){
                dealTimedate[i] = timetoInt(score1[i])%100;
            }
        }
    }

    private int timetoInt(String a){
        return Integer.parseInt(a.split(":")[0] + a.split(":")[1]);
    }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#F81945"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChartView.setInteractive(true);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setMaxZoom((float) 2);//最大方法比例
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.setLineChartData(data);
        lineChartView.setVisibility(View.VISIBLE);

        /*Axis aaxisY = new Axis().setHasLines(true);
        aaxisY.setMaxLabelChars(6);//max label length, for example 60
        List<AxisValue> values = new ArrayList<>();
        for(int i = 0; i < 100; i+= 10){
            AxisValue value = new AxisValue(i);
            String label = "";
            value.setLabel(label);
            values.add(value);
        }
        aaxisY.setValues(values);*/
    }
}


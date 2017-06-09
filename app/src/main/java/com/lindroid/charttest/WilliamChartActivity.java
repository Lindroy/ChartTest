package com.lindroid.charttest;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.db.chart.animation.Animation;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;
import com.lindroid.charttest.util.DisplayUtil;

public class WilliamChartActivity extends AppCompatActivity {
    private LineChartView lineChartView;
    private String[] labels = {"SUN", "MON", "TUE", "WEN", "THU", "FRI", "SAT"};
    //    private float[] values = {0.4f, 1.7f, 1.8f, 2.8f, 2.2f, 3.1f, 3.7f};
    private float[] values = {400f, 1700f, 1800f, 2800f, 2200f, 3100f, 3700f};
//    private int colorLine = 0x2EC3CD;
//    private int colorGrid = 0x9AF4FC;
//    private int colorFill = 0xB09AF4FC;
    private int colorLine = Color.parseColor("#D42EC3CD");
    private int colorGrid = Color.parseColor("#B49AF4FC");
    private int colorFill = Color.parseColor("#8e9af4fc");
    private int colorBottom = Color.parseColor("#7ec8fbff");
    private int[] colors = {this.colorLine, this.colorGrid, this.colorBottom};
    //    private int[] colors = {Color.RED, Color.GREEN, Color.BLACK,Color.BLUE};
//    private float[] positions = {45f, 30f, 10f,0.5f};
    private float[] positions = {0.0f, 32.0f, 5.0f, 55.0f};
    private Tooltip tooltip;
    private LineSet dataSet;
    private Runnable chartAction;
    private Point point;
    private int currentIndex = labels.length / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_williamchart);
        lineChartView = (LineChartView) findViewById(R.id.linechart);
        dataSet = new LineSet(labels, values);

        //将点击区域限制在圆点的附近
        lineChartView.setClickablePointRadius(DisplayUtil.dp2px(this, 16));
        lineChartView.setClickable(false);

        /**设置点的样式**/
        initDots();


        /**设置线的样式**/
//        dataSet.setDashed(values);//虚线

        dataSet.setSmooth(false); //是否为圆滑曲线
        dataSet.setThickness(DisplayUtil.dp2px(this, 2));
        dataSet.setColor(getResources().getColor(R.color.color_line));
        dataSet.beginAt(0);
        dataSet.endAt(labels.length);

        /**设置折线下方区域的填充颜色**/
        dataSet.setFill(getResources().getColor(R.color.color_line));
        dataSet.setGradientFill(colors, null);

        /**坐标轴**/
        lineChartView.setXAxis(true);
        lineChartView.setYAxis(false);
        lineChartView.setAxisColor(getResources().getColor(R.color.color_grid));
        lineChartView.setAxisThickness(DisplayUtil.dp2px(this, 1));
        lineChartView.setBorderSpacing(DisplayUtil.dp2px(this, 0));//折线与坐标轴的距离
        lineChartView.setTopSpacing(DisplayUtil.dp2px(this, 0));//坐标轴最后一点与右边框的距离
        lineChartView.setStep(1000); //坐标点的差值
        lineChartView.setAxisBorderValues(0, 4000); //坐标的最大值和最小值

        /**填充数据和显示图表**/
        lineChartView.addData(dataSet);


        /**绘制网格线**/
        final Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_grid));
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        lineChartView.setGrid(8, 6, paint); //注：网格线不包括坐标轴

        /**设置阈值线，在两条线之间填充颜色**/
//        lineChartView.setValueThreshold(25, 30, paint);

        /**设置坐标刻度样式**/
        lineChartView.setYLabels(AxisRenderer.LabelPosition.OUTSIDE);
        lineChartView.setXLabels(AxisRenderer.LabelPosition.OUTSIDE);
        lineChartView.setFontSize(DisplayUtil.dp2px(this, 12));
        lineChartView.setLabelsColor(Color.GRAY);
//        lineChartView.setLabelsFormat(new DecimalFormat("#.##E0"));


        initTooltip();

//        lineChartView.setTooltips(tooltip).show(new Animation().setInterpolator(new BounceInterpolator())
//                .fromAlpha(0)
//                .withEndAction(chartAction));
        lineChartView.setTooltips(tooltip).show(new Animation().withEndAction(chartAction));


        /**
         * 点的点击事件
         */
        lineChartView.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                for (int i = 0; i < values.length; i++) {
                    point = (Point) dataSet.getEntry(i);
                    if (i == entryIndex) {
                        point.setColor(getResources().getColor(R.color.color_line));
                        point.setRadius(DisplayUtil.dp2px(WilliamChartActivity.this, 4));
                    } else {
                        point.setColor(Color.WHITE);
                        point.setRadius(DisplayUtil.dp2px(WilliamChartActivity.this, 3));
                    }
                }
                Log.e("Tag", "当前点的横坐标=" + labels[entryIndex]);
                Log.e("Tag", "当前点的纵坐标=" + values[entryIndex]);
            }
        });

        lineChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag", "点击了");
                tooltip.setOn(true);
            }
        });
    }

    private void initTooltip() {
        tooltip = new Tooltip(this, R.layout.linechart_tooltip, R.id.tv_tip);
        tooltip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        tooltip.setDimensions(DisplayUtil.dp2px(this, 40), DisplayUtil.dp2px(this, 20));

        //界面显示时，在下标为3的点上弹出ToolTip
        chartAction = new Runnable() {
            @Override
            public void run() {
                tooltip.prepare(lineChartView.getEntriesArea(0).get(currentIndex), values[currentIndex]); //界面显示时，在下标为3的点上弹出ToolTip
                point = (Point) dataSet.getEntry(currentIndex);
                point.setColor(getResources().getColor(R.color.color_line));
                point.setRadius(DisplayUtil.dp2px(WilliamChartActivity.this, 4));
                Log.e("Tag", "mChart.getEntriesArea(0)=" + lineChartView.getEntriesArea(0));
                lineChartView.showTooltip(tooltip, true);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            tooltip.setPivotX(Tools.fromDpToPx(50) / 2);
            tooltip.setPivotY(Tools.fromDpToPx(25));
        }
    }

    /**
     * 设置点的样式
     */
    private void initDots() {
        dataSet.setDotsColor(Color.WHITE);
        dataSet.setDotsRadius(DisplayUtil.dp2px(this, 3));
        dataSet.setDotsStrokeThickness(DisplayUtil.dp2px(this, 2));
        dataSet.setDotsStrokeColor(getResources().getColor(R.color.color_line));
    }

    public void showAnimation(View view) {
        lineChartView.removeAllViewsInLayout();
    }
}

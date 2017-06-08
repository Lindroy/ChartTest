package com.lindroid.charttest;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.db.chart.animation.Animation;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.lindroid.charttest.util.DisplayUtil;

public class WilliamChartActivity extends AppCompatActivity {
    private LineChartView lineChartView;
    private String[] labels = {"MON", "TUE", "WEN", "THU", "FRI", "SAT", "SUN"};
    private float[] values = {5f, 10f, 25f, 15f, 30f, 55f, 25f};
    //    private float[] values = {55f, 55f, 55f, 55f, 55f, 55f, 55f};
//    private int colorLine = 0x2EC3CD;
//    private int colorGrid = 0x9AF4FC;
//    private int colorFill = 0xB09AF4FC;
    private int colorLine = Color.parseColor("#D42EC3CD");
    private int colorGrid = Color.parseColor("#B49AF4FC");
    private int colorFill = Color.parseColor("#8e9af4fc");
    private int colorBottom = Color.parseColor("#7ec8fbff");
    private int[] colors = {this.colorLine, this.colorGrid, this.colorFill, this.colorBottom};
    //    private int[] colors = {Color.RED, Color.GREEN, Color.BLACK,Color.BLUE};
//    private float[] positions = {45f, 30f, 10f,0.5f};
    private float[] positions = {0.0f, 32.0f, 5.0f, 55.0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_williamchart);
        lineChartView = (LineChartView) findViewById(R.id.linechart);
        LineSet dataSet = new LineSet(labels, values);

        /**设置点的样式**/
        dataSet.setDotsColor(Color.WHITE);
        dataSet.setDotsRadius(DisplayUtil.dp2px(this, 4));
        dataSet.setDotsStrokeThickness(DisplayUtil.dp2px(this, 2));
        dataSet.setDotsStrokeColor(getResources().getColor(R.color.color_line));

        /**设置线的样式**/
//        dataSet.setDashed(values);//虚线

        dataSet.setSmooth(false); //是否为圆滑曲线
        dataSet.setThickness(DisplayUtil.dp2px(this, 2));
        dataSet.setColor(getResources().getColor(R.color.color_line));
        dataSet.beginAt(0);
        dataSet.endAt(labels.length);

        /**设置折线下方区域的填充颜色**/
//        dataSet.setFill(getResources().getColor(R.color.color_fill));
        dataSet.setGradientFill(colors, positions);

        /**坐标轴**/
        lineChartView.setXAxis(true);
        lineChartView.setYAxis(true);
        lineChartView.setAxisColor(getResources().getColor(R.color.color_grid));
        lineChartView.setAxisThickness(DisplayUtil.dp2px(this, 1));
        lineChartView.setBorderSpacing(DisplayUtil.dp2px(this, 0));//折线与坐标轴的距离
        lineChartView.setTopSpacing(DisplayUtil.dp2px(this, 0));//坐标轴最后一点与右边框的距离
        lineChartView.setStep(10); //坐标点的差值
        lineChartView.setAxisBorderValues(0, 65); //坐标的最大值和最小值

        /**填充数据和显示图表**/
        lineChartView.addData(dataSet);
        lineChartView.show();

        /**绘制网格线**/
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_grid));
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        lineChartView.setGrid(7, 6, paint); //注：网格线不包括坐标轴

        /**设置阈值线，在两条线之间填充颜色**/
//        lineChartView.setValueThreshold(25, 30, paint);

        /**设置坐标刻度样式**/
        lineChartView.setYLabels(AxisRenderer.LabelPosition.OUTSIDE);
        lineChartView.setXLabels(AxisRenderer.LabelPosition.OUTSIDE);
        lineChartView.setFontSize(DisplayUtil.dp2px(this, 12));
        lineChartView.setLabelsColor(Color.GRAY);

        Animation animation = new Animation();
        animation.setDuration(2000);

        /**
         * 点的点击事件
         */
        lineChartView.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
//                Log.e("Tag","setIndex="+setIndex);
//                Log.e("Tag","entryIndex="+entryIndex);
//                Log.e("Tag","rect="+rect);
                Log.e("Tag", "当前点的横坐标=" + labels[entryIndex]);
                Log.e("Tag", "当前点的纵坐标=" + values[entryIndex]);
                Toast.makeText(WilliamChartActivity.this, "当前点的横坐标=" + labels[entryIndex] + "," + "纵坐标=" + values[entryIndex], Toast.LENGTH_SHORT).show();
            }
        });
    }
}

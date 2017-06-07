package com.lindroid.charttest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;

public class WilliamChartActivity extends AppCompatActivity {
    private LineChartView lineChartView;
    private String[] labels = {"Mon","Tue","Wen","Thu","Fri","Sat","Sun"};
    private float[] values = {5f,10f,25f,15f,30f,55f,25f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_williamchart);
        lineChartView = (LineChartView) findViewById(R.id.linechart);
        LineSet dataSet = new LineSet(labels,values);

        /**设置点的样式**/
        dataSet.setDotsColor(Color.BLUE);
        dataSet.setDotsRadius(10);
        dataSet.setDotsStrokeThickness(5);
        dataSet.setDotsStrokeColor(Color.YELLOW);

        /**设置线的样式**/
//        dataSet.setDashed(0f);

        dataSet.setSmooth(false);
        dataSet.setThickness(5);
        dataSet.setColor(Color.GREEN);
        dataSet.beginAt(0);
        dataSet.endAt(labels.length);

        /**设置填充颜色**/
        dataSet.setFill(Color.RED);

        lineChartView.addData(dataSet);
        lineChartView.show();
    }
}

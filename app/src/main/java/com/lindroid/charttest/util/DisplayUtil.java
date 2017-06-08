package com.lindroid.charttest.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by linyulong on 2017/5/3.
 * 像素转换工具类
 */

public class DisplayUtil {
    private static String TAG = "Tag";

    /**
     * 将px值转换为dp值
     * scale:换算比例
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.e(TAG, "scale=" + scale);
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.e(TAG, "scale=" + scale);
        return (int) (dpValue * scale + 0.5);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        Log.e(TAG, "fontScale=" + fontScale);
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        Log.e(TAG, "fontScale=" + fontScale);
        return (int) (spValue * fontScale + 0.5);
    }
}

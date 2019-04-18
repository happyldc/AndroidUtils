package com.happyldc.util;

import android.graphics.Color;

/**
 * @author ldc
 * @Created at 2018/5/28 14:11.
 */

public class LinearGradientUtil {
    private int mStartColor;
    private int mEndColor;

    public LinearGradientUtil(int startColor, int endColor) {
        this.mStartColor = startColor;
        this.mEndColor = endColor;
    }

    public void setStartColor(int startColor) {
        this.mStartColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.mEndColor = endColor;
    }

    /**
     * 获取某一个百分比间的颜色 无透明
     * @param radio [0,1]
     * @return
     */
    public int getColor(float radio) {
        int redStart = Color.red(mStartColor);
        int blueStart = Color.blue(mStartColor);
        int greenStart = Color.green(mStartColor);
        int redEnd = Color.red(mEndColor);
        int blueEnd = Color.blue(mEndColor);
        int greenEnd = Color.green(mEndColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb(255, red, greed, blue);
    }

    /**
     * 获取某一个百分比间的颜色
     * @param radio [0,1]
     * @param alpha [0,1] 透明度
     * @return
     */
    public int getColor(float radio,float alpha) {
        int redStart = Color.red(mStartColor);
        int blueStart = Color.blue(mStartColor);
        int greenStart = Color.green(mStartColor);
        int redEnd = Color.red(mEndColor);
        int blueEnd = Color.blue(mEndColor);
        int greenEnd = Color.green(mEndColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb((int)(alpha*255), red, greed, blue);
    }
}

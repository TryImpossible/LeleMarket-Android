package com.bynn.lib_basic.utils;

import android.graphics.Color;

public class ColorUtils {

    /**
     * 透明度变化颜色
     *
     * @param color
     * @param fraction
     * @return
     */
    public static int alphaColor(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
}

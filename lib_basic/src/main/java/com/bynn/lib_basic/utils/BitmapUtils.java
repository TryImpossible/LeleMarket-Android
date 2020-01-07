package com.bynn.lib_basic.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yalantis.ucrop.util.BitmapLoadUtils;

public class BitmapUtils {

    /**
     * 获取bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static Bitmap getBitmap(String filePath, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = BitmapLoadUtils.calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
}

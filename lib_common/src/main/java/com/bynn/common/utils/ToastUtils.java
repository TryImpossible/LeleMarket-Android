package com.bynn.common.utils;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class ToastUtils {

    private volatile static Toast mToast;

    private ToastUtils() {
    }

    private static Toast getToast(Context context) {
        if (null == mToast) {
            synchronized (ToastUtils.class) {
                if (null == mToast) {
                    mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
            }
        }
        return mToast;
    }

    private static void toast(@NonNull Context context, String text, int duration) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            //9.0上系统机制修改，重复toast 会自动调用隐藏上一个， 不能共用一个toast
            Toast.makeText(context, text, duration).show();
        } else {
            Toast toast = getToast(context);
            toast.setText(text);
            toast.setDuration(duration);
            toast.show();
        }
    }

    public static void showShort(@NonNull Context context, @StringRes int resId) {
        showShort(context, context.getString(resId));
    }

    public static void showShort(@NonNull Context context, String message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLong(@NonNull Context context, @StringRes int resId) {
        showShort(context, context.getString(resId));
    }

    public static void showLong(@NonNull Context context, String message) {
        toast(context, message, Toast.LENGTH_LONG);
    }
}

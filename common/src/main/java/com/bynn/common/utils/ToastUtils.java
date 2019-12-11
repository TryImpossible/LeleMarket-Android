package com.bynn.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils extends Toast {

    private volatile static Toast mToast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    private ToastUtils(Context context) {
        super(context);
    }

    private Toast getInstance() {
        if (null == mToast) {
            synchronized (ToastUtils.class) {
                if (null == mToast) {
//                    mToast = Toast.makeText();
                }
            }
        }
        return mToast;
    }
}

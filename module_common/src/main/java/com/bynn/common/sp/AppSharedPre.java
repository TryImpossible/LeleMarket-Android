package com.bynn.common.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.sp.BaseSharedPreferences;

public class AppSharedPre extends BaseSharedPreferences {
    private static volatile AppSharedPre sSharedPre;

    // 文件名
    private static final String FILE_NAME = "common_app_data";

    // 刘海屏
    public static final String KEY_NOTCH = "key_notch";
    // 刘海屏顶部高度
    public static final String KEY_NOTCH_TOP = "key_notch_top";
    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_DEVICE_UNIQUE_ID = "key_device_unique_id";
    public static final String KEY_LOCATION = "key_location";
    public static final String KEY_CONFIG = "key_config";


    @Override
    public SharedPreferences getSharedPreference(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static AppSharedPre getInstance() {
        if (sSharedPre == null) {
            synchronized (AppSharedPre.class) {
                if (sSharedPre == null) {
                    sSharedPre = new AppSharedPre();
                }
            }
        }
        return sSharedPre;
    }

    /**
     * 设置是否刘海屏
     *
     * @param isNotch
     * @return
     */
    public boolean setNotch(boolean isNotch) {
        String result = sSharedPre.put(BaseApplication.getContext(), AppSharedPre.KEY_NOTCH, isNotch);
        return AppSharedPre.KEY_NOTCH.equals(result);
    }

    /**
     * 是否刘海屏
     *
     * @return
     */
    public boolean isNotch() {
        return (boolean) sSharedPre.get(BaseApplication.getContext(), AppSharedPre.KEY_NOTCH, false);
    }

    /**
     * 设置刘海屏顶部高度
     *
     * @param height
     * @return
     */
    public boolean setNotchTop(int height) {
        String result = sSharedPre.put(BaseApplication.getContext(), AppSharedPre.KEY_NOTCH_TOP, height);
        return AppSharedPre.KEY_NOTCH_TOP.equals(result);
    }

    /**
     * 刘海屏幕顶部高度
     *
     * @return
     */
    public int getNotchTop() {
        return (int) sSharedPre.get(BaseApplication.getContext(), AppSharedPre.KEY_NOTCH_TOP, 0);
    }
}

package com.bynn.common.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseSharedPreferences;
import com.bynn.common.bean.UserBean;
import com.google.gson.Gson;

public class UserSharedPre extends BaseSharedPreferences {
    public static volatile UserSharedPre sSharedPre;

    public static final String FILE_NAME = "common_share_userss_data";
    public static final String KEY_USER = "key_uesr";

    @Override
    public SharedPreferences getSharedPreference(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static UserSharedPre getInstance() {
        if (sSharedPre == null) {
            synchronized (UserSharedPre.class) {
                if (sSharedPre == null) {
                    sSharedPre = new UserSharedPre();
                }
            }
        }
        return sSharedPre;
    }

    /**
     * 设置用户
     *
     * @param user
     * @return
     */
    public boolean setUser(UserBean user) {
        String userJson = new Gson().toJson(user);
        String result = sSharedPre.put(BaseApplication.getContext(), KEY_USER, userJson);
        return KEY_USER.equals(result);
    }

    /**
     * 获取用户
     *
     * @return
     */
    public UserBean getUser() {
        String userJson = (String) sSharedPre.get(BaseApplication.getContext(), KEY_USER, "");
        return new Gson().fromJson(userJson, UserBean.class);
    }
}

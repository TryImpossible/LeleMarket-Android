package com.bynn.common.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.bynn.common.bean.UserBean;
import com.bynn.common.sp.AppSharedPre;
import com.bynn.common.sp.UserSharedPre;

@Interceptor(name = "login", priority = 1)
public class LoginInterceptorImpl implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        UserBean user = UserSharedPre.getInstance().getUser();
        if (user != null) {
            // 登录不做拦截
            callback.onContinue(postcard);
        } else {
            switch (postcard.getPath()) {
                case MineRoutePath.MINE_COUPON_ACTIVITY:
                    callback.onInterrupt(null);
                    break;
                default:
                    callback.onContinue(postcard);
                    break;
            }
        }
    }

    @Override
    public void init(Context context) {

    }
}

package com.bynn.common.router;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

public class LoginNavigationCallbackImpl implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {
        // 找到了
    }

    @Override
    public void onLost(Postcard postcard) {
        // 没找到
    }

    @Override
    public void onArrival(Postcard postcard) {
       // 跳转成功
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        // 拦截成功
        String path = postcard.getPath();
        Bundle bundle = postcard.getExtras();
        ARouter.getInstance()
                .build(MineRoutePath.MINE_LOGIN_ACTIVITY)
                .with(bundle)
                .withString("targetPath", path)
                .navigation();
    }
}

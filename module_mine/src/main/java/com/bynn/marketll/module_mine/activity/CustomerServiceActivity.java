package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.MineRoutePath;
import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseWebActivity;
import com.bynn.marketll.module_mine.constants.MineConstants;

@Route(path = MineRoutePath.CUSTOMER_SERVICE_ACTIVITY)
public class CustomerServiceActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = BaseApplication.getAppComponent().getBaseUrl() + MineConstants.CUSTOMER_SERVICE_URL;
        loadUrl(url);
    }
}

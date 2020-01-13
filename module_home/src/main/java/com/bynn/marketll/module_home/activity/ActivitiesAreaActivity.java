package com.bynn.marketll.module_home.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.lib_basic.activity.BaseWebActivity;
import com.bynn.lib_basic.utils.StringUtils;

@Route(path = HomeRoutePath.ACTIVITIES_AREA_ACTIVITY)
public class ActivitiesAreaActivity extends BaseWebActivity {

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getStringExtra("url");
        if (!StringUtils.isEmpty(mUrl)) {
            loadUrl(mUrl);
        }
    }
}

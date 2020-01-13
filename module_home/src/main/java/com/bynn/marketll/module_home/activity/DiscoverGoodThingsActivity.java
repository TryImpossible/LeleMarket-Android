package com.bynn.marketll.module_home.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_home.R;

@Route(path = HomeRoutePath.DISCOVER_GOODS_THINGS_ACTIVITY)
public class DiscoverGoodThingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_discover_good_things);
    }
}

package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_mine.R;

@Route(path = MineRoutePath.MINE_COUPON_ACTIVITY)
public class MineCouponActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_mine_coupon);
    }
}

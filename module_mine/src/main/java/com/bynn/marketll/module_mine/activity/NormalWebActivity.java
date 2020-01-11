package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.activity.BaseWebActivity;

@Route(path = MineRoutePath.NORMAL_WEB_ACTIVITY)
public class NormalWebActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra("title");
        setTitle(title);

        String url = getIntent().getStringExtra("url");
        loadUrl(url);
    }
}

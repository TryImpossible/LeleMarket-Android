package com.bynn.marketll.module_discover.activity;

import android.os.Bundle;

import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_discover.R;
import com.bynn.marketll.module_discover.fragment.DiscoverFragment;

public class DiscoverActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, DiscoverFragment.newInstance())
                .commit();
    }
}

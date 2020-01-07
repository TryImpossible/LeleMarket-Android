package com.bynn.marketll.module_home.activity;

import android.os.Bundle;

import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, HomeFragment.newInstance())
                .commit();
    }
}

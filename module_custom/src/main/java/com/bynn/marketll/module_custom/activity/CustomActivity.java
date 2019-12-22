package com.bynn.marketll.module_custom.activity;

import android.os.Bundle;

import com.bynn.common.base.BaseActivity;
import com.bynn.marketll.module_custom.R;
import com.bynn.marketll.module_custom.fragment.CustomFragment;

public class CustomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, CustomFragment.newInstance())
                .commit();
    }
}

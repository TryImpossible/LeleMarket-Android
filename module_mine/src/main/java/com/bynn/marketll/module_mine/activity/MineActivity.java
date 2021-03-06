package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;

import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.fragment.MineFragment;

public class MineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_main);
        translucentStatusBar();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, MineFragment.newInstance())
                .commit();
    }
}

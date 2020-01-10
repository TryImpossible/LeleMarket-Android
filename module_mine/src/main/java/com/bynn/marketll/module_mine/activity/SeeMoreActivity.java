package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = MineRoutePath.SEE_MORE_ACTIVITY)
public class SeeMoreActivity extends BaseActivity {

    @BindView(R2.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_see_more);
        mUnbinder = ButterKnife.bind(this);
    }

}

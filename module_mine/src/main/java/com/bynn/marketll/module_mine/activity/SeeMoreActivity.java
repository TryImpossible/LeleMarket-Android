package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.view.loadstate.LoadStateLayout;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = MineRoutePath.SEE_MORE_ACTIVITY)
public class SeeMoreActivity extends BaseActivity {

    @BindView(R2.id.loadStateLayout) LoadStateLayout mLoadStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_see_more);
    }

    @OnClick({R2.id.btn_loading, R2.id.btn_failure, R2.id.btn_empty, R2.id.btn_no_network})
    public void onButtonClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_loading) {
            mLoadStateLayout.showLoading();
        } else if (id == R.id.btn_failure) {
            mLoadStateLayout.showFailure();
        } else if (id == R.id.btn_empty) {
            mLoadStateLayout.showEmpty();
        } else if (id == R.id.btn_no_network) {
            mLoadStateLayout.showNoNetwork();
        }
    }
}

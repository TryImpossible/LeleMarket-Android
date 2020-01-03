package com.bynn.marketll.module_discover.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.DiscoverRoutePath;
import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.view.loadstate.LoadStateLayout;
import com.bynn.marketll.module_discover.DiscoverPresenter;
import com.bynn.marketll.module_discover.R;
import com.bynn.marketll.module_discover.R2;
import com.bynn.marketll.module_discover.bean.CommodityBean;
import com.bynn.marketll.module_discover.bean.CommodityResult;
import com.bynn.marketll.module_discover.dagger.DaggerDiscoverComponent;
import com.bynn.marketll.module_discover.dagger.DiscoverComponent;
import com.bynn.marketll.module_discover.dagger.DiscoverModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = DiscoverRoutePath.DISCOVER_FRAGMENT)
public class DiscoverFragment extends BaseFragment {

    @BindView(R2.id.loadStateLayout)
    LoadStateLayout mLoadStateLayout;
    @BindView(R2.id.refreshLayout) SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)  RecyclerView       mRecyclerView;

    // 页码，从0开始
    private int               mPage = 0;
    private DiscoverPresenter mDiscoverPresenter;

    public static DiscoverFragment newInstance() {

        Bundle args = new Bundle();

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.discover_fragment_discover, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        injectPresenter();
        initView();
        return view;
    }

    @Override
    public void showProgress() {
        mLoadStateLayout.showLoading();
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof CommodityResult) {
            List<CommodityBean> data = ((CommodityResult) successObj).getData();
            if (data == null || data.size() == 0) {
                mLoadStateLayout.showEmpty();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        mLoadStateLayout.showFailure();
    }

    private void injectPresenter() {
        DiscoverComponent component = DaggerDiscoverComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .discoverModule(new DiscoverModule(this))
                .build();
        mDiscoverPresenter = component.getDiscoverPresenter();
        getLifecycle().addObserver(mDiscoverPresenter);
    }

    private void initView() {
        mLoadStateLayout.getFailureViewBuilder()
                .setOnReloadClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDiscoverPresenter.getFindList(mPage);
                    }
                });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mDiscoverPresenter.getFindList(mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mDiscoverPresenter.getFindList(mPage);
            }
        });
        mRefreshLayout.autoRefresh();
    }

}

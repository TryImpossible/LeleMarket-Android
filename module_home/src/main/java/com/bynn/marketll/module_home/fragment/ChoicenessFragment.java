package com.bynn.marketll.module_home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.view.MyBanner;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.adapter.ChoicenessAdapter;
import com.bynn.marketll.module_home.bean.ChoicenessBean;
import com.bynn.marketll.module_home.bean.ChoicenessResult;
import com.bynn.marketll.module_home.bean.CustomizationBean;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.google.android.material.internal.FlowLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoicenessFragment extends BaseFragment {
    private static final String ID = "id";

    @BindView(R.id.refreshLayout) SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)  RecyclerView       mRecyclerView;
    private                       MyBanner           mBanner;
    private                       FlowLayout         mFlowLayout;

    private Unbinder          mUnbinder;
    private HomePresenter     mHomePresenter;
    private ChoicenessAdapter mAdapter;
    // 表示TopNav类型
    private int               mId;
    private boolean           mIsLoadedData = false;

    public static ChoicenessFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ID, id);
        ChoicenessFragment fragment = new ChoicenessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChoicenessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mId = getArguments().getInt(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_choiceness, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            mIsLoadedData = true;
            injectPresenter();
            initData();
            initView();
        }
    }

    @Override
    public void onDestroy() {
        if (null != mUnbinder) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroy();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        if (null != mRefreshLayout) {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof ChoicenessResult) {
            ChoicenessResult.DataBean data = ((ChoicenessResult) successObj).getData();
            if (null != data) {
                mBanner.setImageList(data.getBannerImageList());
                mBanner.startPlay();

                List<ChoicenessBean> list = new ArrayList<>();
                list.add(new ChoicenessBean(true, getString(R.string.home_label_enable_customization)));
                list.add(new ChoicenessBean(ChoicenessBean.HANDPICK, new ChoicenessBean.Item(data.getHandpick())));
                list.add(new ChoicenessBean(true, getString(R.string.home_label_customization_recommend)));
                for (CustomizationBean bean : data.getCustomization()) {
                    list.add(new ChoicenessBean(ChoicenessBean.CUSTOMIZATION, new ChoicenessBean.Item(bean)));
                }
                mAdapter.setNewData(list);
            }
        }
    }

    private void injectPresenter() {
        HomeComponent component = DaggerHomeComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .homeModule(new HomeModule(this))
                .build();
        mHomePresenter = component.getHomePresenter();
        getLifecycle().addObserver(mHomePresenter);
    }

    private void initData() {
        mHomePresenter.getHomeChoiceness();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new ChoicenessAdapter(new ArrayList<>());
        View headerView = getLayoutInflater().inflate(R.layout.home_header_choiceness, (ViewGroup) mRecyclerView.getParent(), false);
        mBanner = headerView.findViewById(R.id.banner);
        mFlowLayout = headerView.findViewById(R.id.flowLayout);
        mAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mAdapter);
    }

}

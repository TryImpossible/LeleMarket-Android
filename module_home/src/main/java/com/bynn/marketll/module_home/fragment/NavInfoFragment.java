package com.bynn.marketll.module_home.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bynn.common.adapter.GoodsAdapter;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.bean.NavInfoByPageResult;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavInfoFragment extends BaseFragment {
    private static final String ID = "id";

    @BindView(R2.id.refreshLayout) SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)  RecyclerView       mRecyclerView;

    private HomePresenter  mHomePresenter;
    private GoodsAdapter   mAdapter;
    // 表示TopNav类型
    private int            mId;
    // 当前页码, 从0开始
    private int            mPage         = 0;
    // 是否加载过数据
    private boolean        mIsLoadedData = false;

    public NavInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment NavInfoFragment.
     */
    public static NavInfoFragment newInstance(int id) {
        NavInfoFragment fragment = new NavInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_nav_info, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            mIsLoadedData = true;
            injectPresenter();
            initView();
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (null != mRefreshLayout) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof NavInfoByPageResult) {
            List<RecommendGoodsBean> data = ((NavInfoByPageResult) successObj).getData();
            if (mPage == 0) {
                mAdapter.setNewData(data);
            } else {
                mAdapter.addData(data);
            }
            if (data.size() < HomePresenter.PAGE_SIZE) {
                mRefreshLayout.setNoMoreData(true);
            } else {
                mRefreshLayout.setNoMoreData(false);
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

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mHomePresenter.getHomeNavInfoByPage(mId, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mHomePresenter.getHomeNavInfoByPage(mId, mPage);
            }
        });
        mRefreshLayout.autoRefresh();

        mAdapter = new GoodsAdapter(new ArrayList<>());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildLayoutPosition(view);
                int space = QMUIDisplayHelper.dp2px(getContext(), 6);
                if ((int) position / 2 == 0) {
                    outRect.top = space;
                }
                if (position % 2 == 0) {
                    outRect.right = space / 2;
                }
                if (position % 2 == 1) {
                    outRect.left = space / 2;
                }
                outRect.bottom = space;
            }
        });
    }
}

package com.bynn.marketll.module_home.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.adapter.GoodThingsAdapter;
import com.bynn.marketll.module_home.bean.NavInfoByPageResult;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodThingsFragment extends BaseFragment {
    private static final String TYPE_ID = "id";

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private HomePresenter mHomePresenter;
    private GoodThingsAdapter mAdapter;
    // 好物类型ID
    private int mTypeId;
    // 当前页码, 从0开始
    private int mPage = 0;
    // 是否加载过数据
    private boolean mIsLoadedData = false;

    public static GoodThingsFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(TYPE_ID, id);

        GoodThingsFragment fragment = new GoodThingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public GoodThingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeId = getArguments().getInt(TYPE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_good_things, container, false);
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
        if (successObj instanceof RecommendGoodsResult) {
            List<RecommendGoodsBean> data = ((RecommendGoodsResult) successObj).getData();
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
                mHomePresenter.getGoods(mTypeId, mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mHomePresenter.getGoods(mTypeId, mPage);
            }
        });
        mRefreshLayout.autoRefresh();

        mAdapter = new GoodThingsAdapter(new ArrayList<>());
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

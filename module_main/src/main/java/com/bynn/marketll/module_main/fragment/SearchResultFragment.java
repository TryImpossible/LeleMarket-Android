package com.bynn.marketll.module_main.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_main.MainPresenter;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;
import com.bynn.marketll.module_main.activity.SearchActivity;
import com.bynn.marketll.module_main.adapter.SearchKeywordAdapter;
import com.bynn.marketll.module_main.adapter.SearchResultAdapter;
import com.bynn.marketll.module_main.dagger.DaggerMainComponent;
import com.bynn.marketll.module_main.dagger.MainComponent;
import com.bynn.marketll.module_main.dagger.MainModule;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends BaseFragment {

    @BindView(R2.id.refreshLayout) SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)  RecyclerView       mRecyclerView;

    private MainPresenter       mMainPresenter;
    private SearchResultAdapter mResultAdapter;
    // 页码，从0 开始
    private int                 mPage = 0;

    public static SearchResultFragment newInstance() {

        Bundle args = new Bundle();

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment_search_result, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        injectPresenter();
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 0;
        mMainPresenter.getGoodsInfo(mPage, ((SearchActivity)getActivity()).getKeyword());
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof RecommendGoodsResult) {
            List<RecommendGoodsBean> data = ((RecommendGoodsResult) successObj).getData();
            if (data == null) {
                mRefreshLayout.setNoMoreData(true);
                return;
            }
            if (mPage == 0) {
                mResultAdapter.setNewData(data);
                mRefreshLayout.setNoMoreData(false);
            } else {
                mResultAdapter.addData(data);
            }
            if (data.size() < MainPresenter.PAGE_SIZE) {
                mRefreshLayout.setNoMoreData(true);
            }
        }
    }

    private void injectPresenter() {
        MainComponent component = DaggerMainComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .mainModule(new MainModule(this))
                .build();
        mMainPresenter = component.getMainPresenter();
        getLifecycle().addObserver(mMainPresenter);
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mMainPresenter.getGoodsInfo(mPage, ((SearchActivity)getActivity()).getKeyword());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mMainPresenter.getGoodsInfo(mPage, ((SearchActivity)getActivity()).getKeyword());
            }
        });

        mResultAdapter = new SearchResultAdapter(new ArrayList<>());
        mResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mResultAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int space = QMUIDisplayHelper.dp2px(getContext(), 6);
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.top = space;
                }
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
            }
        });
    }

    /**
     * 搜索，提供给外部调用
     */
    public void startSearch() {
        mPage = 0;
        mMainPresenter.getGoodsInfo(mPage, ((SearchActivity)getActivity()).getKeyword());
    }
}

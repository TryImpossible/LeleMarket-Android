package com.bynn.marketll.module_custom.fragment;


import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bynn.common.adapter.GoodsAdapter;
import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.common.view.banner.BannerView;
import com.bynn.common.view.banner.ScaleTransformer;
import com.bynn.marketll.module_custom.CustomPresenter;
import com.bynn.marketll.module_custom.R;
import com.bynn.marketll.module_custom.dagger.CustomComponent;
import com.bynn.marketll.module_custom.dagger.CustomModule;
import com.bynn.marketll.module_custom.dagger.DaggerCustomComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public class GoodsFragment extends BaseFragment {
    private static final String MENU_ID = "menuId";
    private static final String BANNER = "banner";
    private static final String RECOMMENDGOODS = "recommendGoods";

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private GoodsAdapter mGoodsAdapter;
    private CustomPresenter mCustomPresenter;
    // 菜单id
    private int mMenuId;
    //  页码从0 开始
    private int mPage = 0;

    public GoodsFragment() {
        // Required empty public constructor
    }

    public static GoodsFragment newInstance(@NotNull int menuId) {
        Bundle args = new Bundle();
        args.putInt(MENU_ID, menuId);

        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static GoodsFragment newInstance(@NotNull int menuId, ArrayList<BannerBean> bannerBeans, ArrayList<RecommendGoodsBean> recommendGoodsBeans) {
        Bundle args = new Bundle();
        args.putInt(MENU_ID, menuId);
        args.putParcelableArrayList(BANNER, bannerBeans);
        args.putParcelableArrayList(RECOMMENDGOODS, recommendGoodsBeans);

        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMenuId = getArguments().getInt(MENU_ID);
        if (null != getArguments().getParcelableArrayList(RECOMMENDGOODS)) {
            mPage = 1; // 第一页数据，从左侧数据传递过来了，所以从1开始，即第2页
        } else {
            mPage = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.custom_fragment_goods, container, false);
        ButterKnife.bind(this, view);
        injectPresenter();
        initView();
        if (null == getArguments().getParcelableArrayList(RECOMMENDGOODS)) {
            loadData();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }


    @Override
    public void hideProgress() {
        super.hideProgress();
        if (null != mRefreshLayout) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof RecommendGoodsResult) {
            List<RecommendGoodsBean> data = ((RecommendGoodsResult) successObj).getData();
            if (mPage == 0) {
                mGoodsAdapter.setNewData(data);
            } else {
                mGoodsAdapter.addData(data);
            }
            if (data.size() < CustomPresenter.PAGE_SIZE) {
                mRefreshLayout.setNoMoreData(true);
            } else {
                mRefreshLayout.setNoMoreData(false);
            }
        }
    }

    private void injectPresenter() {
        CustomComponent component = DaggerCustomComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .customModule(new CustomModule(this))
                .build();
        mCustomPresenter = component.getCustomPresenter();
        getLifecycle().addObserver(mCustomPresenter);
    }

    private void initView() {
        ArrayList<RecommendGoodsBean> recommendGoodsBeans = getArguments().getParcelableArrayList(RECOMMENDGOODS);
        if (null != recommendGoodsBeans) {
            mGoodsAdapter = new GoodsAdapter(recommendGoodsBeans);
        } else {
            mGoodsAdapter = new GoodsAdapter(new ArrayList<>());
        }
        ArrayList<BannerBean> bannerBeans = getArguments().getParcelableArrayList(BANNER);
        if (null != bannerBeans) {
            List<String> imageList = new ArrayList<>();
            for (BannerBean bean : bannerBeans) {
                imageList.add(bean.getImgUrl());
            }
            BannerView bannerView = new BannerView(getContext());
            bannerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            bannerView.getIndicator().setVisible(false);
            // 禁止裁剪子视图
            bannerView.getViewPgaer().setClipToPadding(false);
            bannerView.getViewPgaer().setPadding(QMUIDisplayHelper.dp2px(getContext(), 16), 0, QMUIDisplayHelper.dp2px(getContext(), 50), 0);
            bannerView.getViewPgaer().setOffscreenPageLimit(3);
            bannerView.getViewPgaer().setPageTransformer(false, new ScaleTransformer());
            bannerView.setImageList(imageList);
            mGoodsAdapter.setHeaderView(bannerView);
        }

        mRecyclerView.setAdapter(mGoodsAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int space = QMUIDisplayHelper.dp2px(getContext(), 6);
                if (position / 2 == 0) {
                    outRect.top = space;
                }
                if (position % 2 == 0) {
                    outRect.left = space;
                }
                outRect.right = space;
                outRect.bottom = space;
            }
        });

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mCustomPresenter.getMenuGoods(mMenuId, mPage);
            }
        });
    }

    private void loadData() {
        mCustomPresenter.getMenuGoods(mMenuId, mPage);
    }
}

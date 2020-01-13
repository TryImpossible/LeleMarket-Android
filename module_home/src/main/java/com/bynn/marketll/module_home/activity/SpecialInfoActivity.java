package com.bynn.marketll.module_home.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.adapter.GoodsAdapter;
import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.common.view.banner.BannerView;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.bean.SpecialInfoResult;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = HomeRoutePath.SPECIAL_INFO_ACTIVITY)
public class SpecialInfoActivity extends BaseActivity {

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView       mRecyclerView;

    private BannerView mBannerView;
    private TextView   mTvTitle;
    private TextView   mTvDesc;

    private int           mId;
    private int           mType;
    private HomePresenter mHomePresenter;
    private GoodsAdapter  mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_special_info);
        mId = getIntent().getIntExtra("id", -1);
        mType = getIntent().getIntExtra("type", -1);
        mUnbinder = ButterKnife.bind(this);
        translucentStatusBar();
        injectPresenter();
        initView();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof SpecialInfoResult) {
            SpecialInfoResult.DataBean dataBean = ((SpecialInfoResult) successObj).getData();
            if (dataBean != null) {
                List<BannerBean> banners = dataBean.getBanners();
                if (banners != null && banners.size() > 0) {
                    mBannerView.setImageList(BannerBean.getBannerImageList(banners));
                    mTvTitle.setText(banners.get(0).getTitle());
                    mTvDesc.setText(banners.get(0).getCont());
                }

                List<RecommendGoodsBean> goods = dataBean.getRecommendGoods();
                if (goods != null && goods.size() > 0) {
                    mAdapter.setNewData(goods);
                }
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
        mAdapter = new GoodsAdapter(new ArrayList<>());
        View headerView = LayoutInflater.from(this).inflate(R.layout.home_item_special_info_header, null);
        mBannerView = headerView.findViewById(R.id.bannerView);
        mTvTitle = headerView.findViewById(R.id.tv_title);
        mTvDesc = headerView.findViewById(R.id.tv_desc);
        mAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int space = QMUIDisplayHelper.dp2px(SpecialInfoActivity.this, 6);
                if (position < 1) {
                    return;
                }
                if (position % 2 == 0) {
                    outRect.left = space / 2;
                }
                if (position % 2 == 1) {
                    outRect.right = space / 2;
                }
                outRect.bottom = space;
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mHomePresenter.getSpecialInfo(mId, mType);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.autoRefresh();
    }

}

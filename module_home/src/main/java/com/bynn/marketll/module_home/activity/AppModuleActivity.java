package com.bynn.marketll.module_home.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.lib_basic.view.HeaderView;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.adapter.AppModuleAdapter;
import com.bynn.marketll.module_home.bean.AppModuleBean;
import com.bynn.marketll.module_home.bean.AppModuleResult;
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

@Route(path = HomeRoutePath.APP_MODULE_ACTIVITY)
public class AppModuleActivity extends BaseActivity {

    @BindView(R2.id.headerView)    HeaderView         mHeaderView;
    @BindView(R2.id.refreshLayout) SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)  RecyclerView       mRecyclerView;

    private HomePresenter    mHomePresenter;
    private AppModuleAdapter mModuleAdapter;
    private ImageView        mHeaderImage;
    // TODO: 先传固定值，不知道哪里的
    private int              moduleId = 1;
    private int              mPage    = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_app_module);
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
        if (successObj instanceof AppModuleResult) {
            AppModuleResult.DataBean dataBean = ((AppModuleResult) successObj).getData();
            List<AppModuleBean> list = new ArrayList<>();
            if (dataBean != null) {
                if (dataBean.getModuleInfo() != null) {
                    Glide.with(this)
                            .load(dataBean.getModuleInfo().getImgUrl())
                            .into(mHeaderImage);
                }
                if (dataBean.getIosGoods() != null && dataBean.getIosGoods().size() > 0) {
                    list.add(new AppModuleBean(true, getString(R.string.home_lable_ios_customization)));
                    for (RecommendGoodsBean bean : dataBean.getIosGoods()) {
                        list.add(new AppModuleBean(bean));
                    }
                }
                if (dataBean.getAndroidGoods() != null && dataBean.getAndroidGoods().size() > 0) {
                    list.add(new AppModuleBean(true, getString(R.string.home_lable_android_customization)));
                    for (RecommendGoodsBean bean : dataBean.getAndroidGoods()) {
                        list.add(new AppModuleBean(bean));
                    }
                }
                mModuleAdapter.setNewData(list);
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
//        mHeaderView.setBackgroundColor(Color.BLACK);

        mModuleAdapter = new AppModuleAdapter(new ArrayList<>());
        View headerView = LayoutInflater.from(this).inflate(R.layout.home_item_app_module_header, null);
        mHeaderImage = headerView.findViewById(R.id.image);
        mModuleAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mModuleAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || (mModuleAdapter.getItem(position - 1) != null && mModuleAdapter.getItem(position - 1).isHeader)) {
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
                if (position < mModuleAdapter.getIosSectionPosition() + 2) {
                    return;
                }
                int space = QMUIDisplayHelper.dp2px(AppModuleActivity.this, 6);
                if (position % 2 == 0) {
                    if (position > mModuleAdapter.getAndroidSectionPosition()) {
                        outRect.left = space / 2;
                    } else {
                        outRect.right = space / 2;
                    }
                }
                if (position % 2 == 1) {
                    if (position > mModuleAdapter.getAndroidSectionPosition()) {
                        outRect.right = space / 2;
                    } else {
                        outRect.left = space / 2;
                    }
                }
                outRect.bottom = space;
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mHomePresenter.getAppModuleById(moduleId, mPage);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.autoRefresh();
    }
}

package com.bynn.marketll.module_custom.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bynn.common.adapter.GoodsAdapter;
import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.view.banner.BannerView;
import com.bynn.common.view.banner.ScaleTransformer;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_custom.CustomPresenter;
import com.bynn.marketll.module_custom.R;
import com.bynn.marketll.module_custom.R2;
import com.bynn.marketll.module_custom.dagger.CustomComponent;
import com.bynn.marketll.module_custom.dagger.CustomModule;
import com.bynn.marketll.module_custom.dagger.DaggerCustomComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create an instance of this fragment.
 */
public class GoodsFragment extends BaseFragment {
    private static final String MENU_ID        = "menuId";
    private static final String BANNER         = "banner";
    private static final String RECOMMENDGOODS = "recommendGoods";

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView       mRecyclerView;

    private GoodsAdapter    mGoodsAdapter;
    private CustomPresenter mCustomPresenter;
    // 菜单id
    private int             mMenuId;
    //  页码从0 开始
    private int             mPage = 0;

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
    public void hideLoading() {
        super.hideLoading();
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
            BannerView BannerView = new BannerView(getContext());
            BannerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dp2px(getContext(), 100)));
            BannerView.setAutoPlay(false);
            BannerView.setLoop(false);
            BannerView.setVisibility(View.VISIBLE);
            BannerView.getIndicator().setVisible(false);
            // 禁止裁剪子视图
            BannerView.getViewPgaer().setClipToPadding(false);
            BannerView.getViewPgaer().setPadding(0, 0, QMUIDisplayHelper.dp2px(getContext(), 50), 0);
            BannerView.getViewPgaer().setOffscreenPageLimit(3);
            BannerView.getViewPgaer().setPageTransformer(false, new ScaleTransformer());
            BannerView.setOnItemClickListener(new BannerView.OnItemClickListener() {
                @Override
                public void OnClick(int position) {

                }
            });
            BannerView.setImageList(BannerBean.getBannerImageList(bannerBeans));
            mGoodsAdapter.addHeaderView(BannerView);
        }

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 1 && null != bannerBeans) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mGoodsAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int space = QMUIDisplayHelper.dp2px(getContext(), 6);
                if (null != bannerBeans) {
                    if (position == 0) {
                        outRect.top = space;
                    }
                    if (position == 0) {
                        outRect.left = space;
                        outRect.right = space;
                    }
                    if (position > 0 && position % 2 == 1) {
                        outRect.left = space;
                        outRect.right = space / 2;
                    }
                    if (position > 0 && position % 2 == 0) {
                        outRect.left = space / 2;
                        outRect.right = space;
                    }
                } else {
                    if ((int) position / 2 == 0) {
                        outRect.top = space;
                    }
                    if (position % 2 == 0) {
                        outRect.left = space;
                        outRect.right = space / 2;
                    }
                    if (position % 2 == 1) {
                        outRect.left = space / 2;
                        outRect.right = space;
                    }
                }
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

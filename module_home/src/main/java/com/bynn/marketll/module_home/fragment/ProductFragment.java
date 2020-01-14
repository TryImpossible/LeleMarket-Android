package com.bynn.marketll.module_home.fragment;


import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bynn.common.adapter.GoodsAdapter;
import com.bynn.common.bean.GoodsBean;
import com.bynn.common.bean.GoodsResult;
import com.bynn.common.view.banner.BannerView;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.lib_basic.qmui.QMUIStatusBarHelper;
import com.bynn.lib_basic.utils.ColorUtils;
import com.bynn.lib_basic.utils.LogUtils;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.activity.ProductIntroductionActivity;
import com.bynn.marketll.module_home.bean.ChartParamBean;
import com.bynn.marketll.module_home.bean.ChartParamResult;
import com.bynn.marketll.module_home.bean.GoodsPropertyBean;
import com.bynn.marketll.module_home.bean.GoodsPropertyResult;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.bynn.marketll.module_home.dialog.GoodsPropertyDialog;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment {
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";

    @BindView(R2.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R2.id.bannerView)
    BannerView mBannerView;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.header)
    LinearLayout mHeader;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.tv_now_price)
    TextView mTvNowPrice;
    @BindView(R2.id.tv_ori_price)
    TextView mTvOriPrice;
    @BindView(R2.id.tv_custom_count)
    TextView mTvCustomCount;
    @BindView(R2.id.tv_postage)
    TextView mTvPostage;
    @BindView(R2.id.tv_activity)
    TextView mTvActivity;
    @BindView(R2.id.ll_params)
    LinearLayout mLlParams;
    @BindView(R2.id.tv_params)
    TextView mTvParams;
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private int mId;
    private int mType;
    private HomePresenter mHomePresenter;
    private GoodsAdapter mAdapter;
    // 导航栏是否透明，默认是
    private boolean mIsHeaderTranslucent = true;
    // 是否加载过数据
    private boolean mIsLoadedData;
    private ChartParamBean mChartParamBean;
    private GoodsPropertyBean mGoodsPropertyBean;

    private GoodsPropertyDialog mGoodsPropertyDialog;

    public static ProductFragment newInstance(int id, int type) {

        Bundle args = new Bundle();
        args.putInt(KEY_ID, id);
        args.putInt(KEY_TYPE, type);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_product, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initNotch();
        injectPresenter();
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            loadData();
            mIsLoadedData = true;
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof GoodsResult) {
            GoodsBean bean = ((GoodsResult) successObj).getData();
            if (bean != null) {
                mTvName.setText(bean.getName());
                SpannableStringBuilder nowPrice = new SpanUtils()
                        .append("￥")
                        .setFontSize(15, true)
                        .append(String.format("%.2f", bean.getNowPrice()))
                        .setFontSize(18, true)
                        .setForegroundColor(ContextCompat.getColor(getContext(), R.color.basic_colorAccent))
                        .create();
                mTvNowPrice.setText(nowPrice);
                mTvOriPrice.setText(new SpanUtils().append(String.format("￥%.2f", bean.getOrg_price())).setStrikethrough().create());
                mTvCustomCount.setText(String.format(getString(R.string.common_label_already_sell_count), bean.getSell()));
                mTvPostage.setText(bean.isFree() ? getString(R.string.home_lable_free_postage) : getString(R.string.home_lable_free_postage));
                SpannableStringBuilder activity = new SpanUtils()
                        .append(getString(R.string.home_lable_sales))
                        .setForegroundColor(ContextCompat.getColor(getContext(), R.color.basic_text_light))
                        .append(" ")
                        .append(bean.getActivity())
                        .setForegroundColor(ContextCompat.getColor(getContext(), R.color.basic_colorAccent))
                        .create();
                mTvActivity.setText(activity);
                mAdapter.setNewData(bean.getRecommend());
            }
        }
        if (successObj instanceof ChartParamResult) {
            mChartParamBean = ((ChartParamResult) successObj).getData();
            if (mChartParamBean != null) {
                mBannerView.setImageList(mChartParamBean.getImgs());
                ((ProductIntroductionActivity) getActivity()).setDetailUrl(mChartParamBean.getDetailURLStr());
                ((ProductIntroductionActivity) getActivity()).setCommentUrl(mChartParamBean.getAppraiseURLStr());
            }
        }
        if (successObj instanceof GoodsPropertyResult) {
            mGoodsPropertyBean = ((GoodsPropertyResult) successObj).getData();
        }
    }

    @OnClick({R2.id.ll_params})
    public void onViewClick(View view) {
        if (view.getId() == R.id.ll_params) {
            if (mGoodsPropertyDialog == null) {
                mGoodsPropertyDialog = GoodsPropertyDialog.newInstance(mGoodsPropertyBean);
            }
            ((ProductIntroductionActivity) getActivity()).transform();
            mGoodsPropertyDialog.show(getFragmentManager(), GoodsPropertyDialog.TAG);
        }
    }

    public void initNotch() {
        int top = QMUIStatusBarHelper.getStatusbarHeight(getContext());
        mHeader.setPadding(0, top, 0, 0);
    }

    private void injectPresenter() {
        HomeComponent component = DaggerHomeComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .homeModule(new HomeModule(this))
                .build();
        mHomePresenter = component.getHomePresenter();
        getLifecycle().addObserver(mHomePresenter);
    }

    private void loadData() {
        mId = getArguments().getInt(KEY_ID);
        mType = getArguments().getInt(KEY_TYPE);
        mHomePresenter.getPointGood(mId, mType, 9956133);
        mHomePresenter.chartParam2(mType, mId);
        mHomePresenter.goodsProperty(mId);
    }

    private void initView() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = mAppBarLayout.getTotalScrollRange();
                LogUtils.i(verticalOffset + "-----" + totalScrollRange);
                mToolbar.setBackgroundColor(ColorUtils.alphaColor(Color.parseColor("#fafafa"), Math.abs(verticalOffset * 1.0f) / totalScrollRange));
                mIsHeaderTranslucent = Math.abs(verticalOffset) != totalScrollRange;
                ((ProductIntroductionActivity) getActivity()).resetHeaderStyle(mIsHeaderTranslucent);
            }
        });

        mAdapter = new GoodsAdapter(new ArrayList<>());
        View headerView = new View(getContext());
        mAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
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
                int position = parent.getChildLayoutPosition(view);
                int space = QMUIDisplayHelper.dp2px(getContext(), 6);
                if (position < 1) {
                    return;
                }
                if (position % 2 == 1) {
                    outRect.right = space / 2;
                }
                if (position % 2 == 0) {
                    outRect.left = space / 2;
                }
                outRect.bottom = space;
            }
        });
    }

    /**
     * 导航栏是否透明，供外部调用
     *
     * @return
     */
    public boolean isHeaderTranslucent() {
        return mIsHeaderTranslucent;
    }
}

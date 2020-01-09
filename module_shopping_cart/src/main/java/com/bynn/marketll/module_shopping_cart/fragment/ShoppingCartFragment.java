package com.bynn.marketll.module_shopping_cart.fragment;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.ShoppingCartRoutePath;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.lib_basic.view.HeaderView;
import com.bynn.marketll.module_shopping_cart.R;
import com.bynn.marketll.module_shopping_cart.R2;
import com.bynn.marketll.module_shopping_cart.ShoppingCartPresenter;
import com.bynn.marketll.module_shopping_cart.adapter.ShoppingCartAdapter;
import com.bynn.marketll.module_shopping_cart.bean.ShopBean;
import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartBean;
import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartEntity;
import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartResult;
import com.bynn.marketll.module_shopping_cart.dagger.DaggerShoppingCartComponent;
import com.bynn.marketll.module_shopping_cart.dagger.ShoppingCartComponent;
import com.bynn.marketll.module_shopping_cart.dagger.ShoppingCartModule;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = ShoppingCartRoutePath.SHOPPING_CART_FRAGMENT)
public class
ShoppingCartFragment extends BaseFragment {

    @BindView(R2.id.headerView)    HeaderView         mHeaderView;
    //    @BindView(R2.id.loadStateLayout) LoadStateLayout    mLoadStateLayout;
    @BindView(R2.id.refreshLayout) SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.rccyclerView)  RecyclerView       mRecyclerView;
    @BindView(R2.id.cb_all)        CheckBox           mCbAll;
    @BindView(R2.id.cl_bottom)     ConstraintLayout   mClBottom;
    @BindView(R2.id.tv_total)      TextView           mTvTotal;
    @BindView(R2.id.tv_discount)   TextView           mTvDiscount;
    @BindView(R2.id.btn_settle)    Button             mBtnSettle;

    private ShoppingCartPresenter mCartPresenter;
    private ShoppingCartAdapter   mCartAdapter;
    private boolean               mIsEditMode = false;

    public static ShoppingCartFragment newInstance() {

        Bundle args = new Bundle();

        ShoppingCartFragment fragment = new ShoppingCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shopping_fragment_shopping_cart, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        injectPresenter();
        initView();
        return view;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof ShoppingCartResult) {
            List<ShoppingCartBean> list = ((ShoppingCartResult) successObj).getData();
            if (list == null || list.size() == 0) {
                mHeaderView.setMenuVisible(false);
                mClBottom.setVisibility(View.GONE);
                return;
            }

            mHeaderView.setMenuVisible(true);
            mHeaderView.setMenuText(getString(R.string.shopping_label_edit));

            List<ShoppingCartEntity> data = new ArrayList<>();
            for (ShoppingCartBean cartBean : list) {
                data.add(new ShoppingCartEntity(true, cartBean.getCompanyName()));
                for (ShopBean shopBean : cartBean.getShopList()) {
                    data.add(new ShoppingCartEntity(shopBean));
                }
            }
            mCartAdapter.setNewData(data);
            mClBottom.setVisibility(View.VISIBLE);
        }
    }

    private void injectPresenter() {
        ShoppingCartComponent component = DaggerShoppingCartComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .shoppingCartModule(new ShoppingCartModule(this))
                .build();
        mCartPresenter = component.getShoppingCartPresenter();
        getLifecycle().addObserver(mCartPresenter);
    }

    private void initView() {
        mHeaderView.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsEditMode = !mIsEditMode;
                mHeaderView.setMenuText(getString(!mIsEditMode ? R.string.shopping_label_edit : R.string.shopping_label_finish));
                mCartAdapter.enableEditMode(mIsEditMode);
                mTvTotal.setVisibility(mIsEditMode ? View.GONE : View.VISIBLE);
                mTvDiscount.setVisibility(mIsEditMode ? View.GONE : View.VISIBLE);
                mBtnSettle.setText(mIsEditMode ? getString(R.string.shopping_label_delete) : getString(R.string.shopping_label_settle));
            }
        });

        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mCartPresenter.getShoppingCartList(9956133);
            }
        });

        mCartAdapter = new ShoppingCartAdapter(new ArrayList<>());
        mCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_attribute && mIsEditMode) {
                    // TODO:弹窗
                    showToast("弹窗");
                }
            }
        });
        View footView = new View(getContext());
        footView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dp2px(getContext(), 30)));
        mCartAdapter.addFooterView(footView);

        mRecyclerView.setAdapter(mCartAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position < 0 || position > mCartAdapter.getData().size() - 1) {
                    return;
                }
                if (position > 0 && mCartAdapter.getItem(position).isHeader) {
                    outRect.top = QMUIDisplayHelper.dp2px(getContext(), 12);
                }
                outRect.bottom = QMUIDisplayHelper.dp2px(getContext(), 1);
            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int count = parent.getChildCount();

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.basic_white));
                for (int i = 0; i < count; i++) {
                    View child = parent.getChildAt(i);
                    if (child == null) {
                        continue;
                    }
                    Rect rect = new Rect(child.getLeft(), child.getBottom(), child.getLeft() + QMUIDisplayHelper.dp2px(getContext(), 12), child.getBottom() + QMUIDisplayHelper.dp2px(getContext(), 1));
                    c.drawRect(rect, paint);
                }
            }
        });

        mCartPresenter.getShoppingCartList(9956133);

//        EmptyView.Builder emptyView = mLoadStateLayout.getEmptyViewBuilder();
//        emptyView.setIcon(R.mipmap.shopping_ic_empty_cart)
//                .setPrompt(R.string.shopping_label_empty_cart)
//                .setButtonText(R.string.shopping_button_go)
//                .setButtonVisible(true)
//                .setOnButtonClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("跳转至首页");
//                    }
//                });
//        mLoadStateLayout.showEmpty();
    }
}

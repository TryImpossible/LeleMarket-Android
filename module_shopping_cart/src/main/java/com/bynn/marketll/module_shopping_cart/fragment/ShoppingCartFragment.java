package com.bynn.marketll.module_shopping_cart.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.ShoppingCartRoutePath;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.view.loadstate.EmptyView;
import com.bynn.common.view.loadstate.LoadStateLayout;
import com.bynn.marketll.module_shopping_cart.R;
import com.bynn.marketll.module_shopping_cart.R2;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = ShoppingCartRoutePath.SHOPPING_CART_FRAGMENT)
public class
ShoppingCartFragment extends BaseFragment {

    @BindView(R2.id.loadStateLayout)
    LoadStateLayout mLoadStateLayout;

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
        initView();
        return view;
    }

    private void initView() {
        EmptyView.Builder emptyView = mLoadStateLayout.getEmptyViewBuilder();
        emptyView.setIcon(R.mipmap.shopping_ic_empty_cart)
                .setPrompt(R.string.shopping_label_empty_cart)
                .setButtonText(R.string.shopping_button_go)
                .setButtonVisible(true)
                .setOnButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("跳转至首页");
                    }
                });
        mLoadStateLayout.showEmpty();
    }
}

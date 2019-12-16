package com.bynn.marketll.module_shopping_cart.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.ShoppingCartRoutePath;
import com.bynn.common.base.BaseFragment;
import com.bynn.marketll.module_shopping_cart.R;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = ShoppingCartRoutePath.SHOPPING_CART_FRAGMENT)
public class ShoppingCartFragment extends BaseFragment {
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
        return inflater.inflate(R.layout.shopping_fragment_shopping_cart, container, false);
    }

}

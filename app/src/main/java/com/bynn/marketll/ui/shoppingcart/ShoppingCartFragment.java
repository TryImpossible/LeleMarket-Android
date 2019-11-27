package com.bynn.marketll.ui.shoppingcart;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.marketll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

}

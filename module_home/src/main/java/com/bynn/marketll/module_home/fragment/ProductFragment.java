package com.bynn.marketll.module_home.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUINotchHelper;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.activity.ProductIntroductionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment {

    public static ProductFragment newInstance() {

        Bundle args = new Bundle();

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
        initView();
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ((ProductIntroductionActivity) getActivity()).resetHeaderStyle(true);
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            ((ProductIntroductionActivity) getActivity()).resetHeaderStyle(true);
//        }
//    }

    public void initNotch() {

    }

    private void initView() {
    }

}

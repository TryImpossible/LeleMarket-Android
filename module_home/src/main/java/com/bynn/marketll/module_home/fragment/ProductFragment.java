package com.bynn.marketll.module_home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIStatusBarHelper;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment {

    @BindView(R2.id.header)
    LinearLayout mHeader;

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

    public void initNotch() {
        int top = QMUIStatusBarHelper.getStatusbarHeight(getContext());
        mHeader.setPadding(0, top,0,0);
    }

    private void initView() {
    }

}

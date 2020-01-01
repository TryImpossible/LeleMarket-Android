package com.bynn.marketll.module_discover.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.DiscoverRoutePath;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.utils.ToastUtils;
import com.bynn.common.view.loadstate.LoadStateLayout;
import com.bynn.marketll.module_discover.R;
import com.bynn.marketll.module_discover.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = DiscoverRoutePath.DISCOVER_FRAGMENT)
public class DiscoverFragment extends BaseFragment {

    @BindView(R2.id.loadStateLayout)
    LoadStateLayout mLoadStateLayout;

    public static DiscoverFragment newInstance() {

        Bundle args = new Bundle();

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.discover_fragment_discover, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mLoadStateLayout.getFailureViewBuilder()
                .setOnReloadClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("重试");
                    }
                });
        mLoadStateLayout.showFailure();
    }
}

package com.bynn.marketll.module_home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.HomeRoutePath;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.utils.ToastUtils;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = HomeRoutePath.HOME_FRAGMENT)
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.text) TextView text;

    private Unbinder mUnbinder;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        if (null != mUnbinder) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroy();
    }

    @OnClick(R2.id.text)
    public void click() {
        ToastUtils.showShort(getContext(), "123");
    }
}

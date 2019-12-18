package com.bynn.marketll.module_home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.view.MyBanner;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoicenessFragment extends BaseFragment {
    private static final String ID = "id";

    @BindView(R.id.banner)
    MyBanner mBanner;

    private Unbinder      mUnbinder;
    private HomePresenter mHomePresenter;
    // 表示TopNav类型
    private int mId;

    public static ChoicenessFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        ChoicenessFragment fragment = new ChoicenessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChoicenessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mId = getArguments().getInt(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_choiceness, container, false);
        ButterKnife.bind(this, view);
        injectPresenter();
        initData();
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

    private void injectPresenter() {
        HomeComponent component = DaggerHomeComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .homeModule(new HomeModule(this))
                .build();
        mHomePresenter = component.getHomePresenter();
        getLifecycle().addObserver(mHomePresenter);
    }

    private void initData() {
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576690815768&di=baa04edd442026ceeeb988b98f1141e8&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fb8a2f3cb90ebfdcc8f432e55137d8008d8e0b53c656d-LYlEC1_fw658");
        }
        mBanner.setImageList(imageList);
        mBanner.setAutoPlay(true);
    }
}

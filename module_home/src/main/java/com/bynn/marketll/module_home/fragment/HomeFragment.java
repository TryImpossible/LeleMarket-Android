package com.bynn.marketll.module_home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.HomeRoutePath;
import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.bean.TopNavBean;
import com.bynn.marketll.module_home.bean.TopNavResult;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = HomeRoutePath.HOME_FRAGMENT)
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.iv_scan_qrcode) ImageView mIvScanQrcode;
    @BindView(R2.id.et_keyword)     EditText  mEtKeyword;
    @BindView(R2.id.iv_news)        ImageView mIvNews;
    @BindView(R2.id.tabLayout)      TabLayout mTabLayout;
    @BindView(R2.id.viewPager)      ViewPager mViewPager;

    private Unbinder                  mUnbinder;
    private HomePresenter             mHomePresenter;
    private List<String>              mTitleList;
    private List<Fragment>            mFragmentList;
    private FragmentStatePagerAdapter mPagerAdapter;

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
        injectPresenter();
        loadData();
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

    private void loadData() {
        mHomePresenter.getTopNav();
    }

    private void initView(List<TopNavBean> topNavBeanList) {
        if (null == mTitleList) {
            mTitleList = new ArrayList<>();
        }
        if (null == mFragmentList) {
            mFragmentList = new ArrayList<>();
        }
        for (TopNavBean bean : topNavBeanList) {
            mTitleList.add(bean.getName());
            if (topNavBeanList.indexOf(bean) == 0) {
                mFragmentList.add(ChoicenessFragment.newInstance(bean.getId()));
            } else {
                mFragmentList.add(new OtherFragment());
            }
        }

        mPagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R2.id.iv_scan_qrcode)
    public void onScanClicked() {
        showToast("扫码");
    }

    @OnClick(R2.id.et_keyword)
    public void onSearchClicked() {
        showToast("搜索");
    }

    @OnClick(R2.id.iv_news)
    public void onNewsClicked() {
        showToast("消息");
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof TopNavResult) {
            TopNavResult.DataBean data = ((TopNavResult) successObj).getData();
            if (null != data && null != data.getTopNav() && data.getTopNav().size() > 0) {
                initView(data.getTopNav());
            }
        }
    }
}

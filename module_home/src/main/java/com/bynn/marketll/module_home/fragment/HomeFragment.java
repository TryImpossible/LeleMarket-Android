package com.bynn.marketll.module_home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.common.router.MainRoutePath;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = HomeRoutePath.HOME_FRAGMENT)
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.iv_scan_qrcode) ImageView mIvScanQrcode;
    @BindView(R2.id.tv_keyword)     TextView  mTvKeyword;
    @BindView(R2.id.iv_news)        ImageView mIvNews;
    @BindView(R2.id.tabLayout)      TabLayout mTabLayout;
    @BindView(R2.id.divider)        View      mDivider;
    @BindView(R2.id.viewPager)      ViewPager mViewPager;

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
                mFragmentList.add(NavInfoFragment.newInstance(bean.getId()));
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
        mViewPager.setOffscreenPageLimit(topNavBeanList.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);

        if (mTabLayout.getVisibility() == View.GONE) {
            mTabLayout.setVisibility(View.VISIBLE);
        }
        if (mDivider.getVisibility() == View.GONE) {
            mDivider.setVisibility(View.VISIBLE);
        }
        if (mViewPager.getVisibility() == View.GONE) {
            mViewPager.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R2.id.iv_scan_qrcode)
    public void onScanClicked() {
        BaseApplication.getARouter().build(MainRoutePath.SCAN_CODE_ACTIVITY).navigation();
    }

    @OnClick(R2.id.tv_keyword)
    public void onSearchClicked() {
        BaseApplication.getARouter().build(MainRoutePath.SEARCH_ACTIVITY).navigation();
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

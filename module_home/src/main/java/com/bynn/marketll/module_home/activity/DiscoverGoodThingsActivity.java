package com.bynn.marketll.module_home.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.view.HeaderView;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.bean.GoodsTypeBean;
import com.bynn.marketll.module_home.bean.GoodsTypeResult;
import com.bynn.marketll.module_home.dagger.DaggerHomeComponent;
import com.bynn.marketll.module_home.dagger.HomeComponent;
import com.bynn.marketll.module_home.dagger.HomeModule;
import com.bynn.marketll.module_home.fragment.GoodThingsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

@Route(path = HomeRoutePath.DISCOVER_GOODS_THINGS_ACTIVITY)
public class DiscoverGoodThingsActivity extends BaseActivity {

    @BindView(R2.id.headerView)
    HeaderView mHeaderView;
    @BindView(R2.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R2.id.viewPager)
    ViewPager mViewPager;

    private HomePresenter mHomePresenter;
    private List<String> mTitleList;
    private List<GoodThingsFragment> mFragmentList;
    private FragmentPagerAdapter mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_discover_good_things);
        injectPresenter();
        loadData();
        String title = getIntent().getStringExtra("title");
        mHeaderView.setTitleText(title);
        mHeaderView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof GoodsTypeResult) {
            GoodsTypeResult.DataBean dataBean = ((GoodsTypeResult) successObj).getData();
            if (dataBean != null) {
                initView(dataBean.getTypes());
            }
        }
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
        mHomePresenter.getGoodsType();
    }

    private void initView(List<GoodsTypeBean> list) {
        if (mTitleList == null) {
            mTitleList = new ArrayList<>();
        }
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        for (GoodsTypeBean bean : list) {
            mTitleList.add(bean.getName());
            mFragmentList.add(GoodThingsFragment.newInstance(bean.getId()));
        }

        mAdpater = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        };

        mViewPager.setAdapter(mAdpater);
        mViewPager.setOffscreenPageLimit(list.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);

        if (mTabLayout.getVisibility() == View.GONE) {
            mTabLayout.setVisibility(View.VISIBLE);
        }
        if (mViewPager.getVisibility() == View.GONE) {
            mViewPager.setVisibility(View.VISIBLE);
        }
    }
}

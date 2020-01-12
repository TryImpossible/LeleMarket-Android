package com.bynn.marketll.module_home.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.router.HomeRoutePath;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.qmui.QMUINotchHelper;
import com.bynn.lib_basic.qmui.QMUIStatusBarHelper;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.dialog.ShareDialog;
import com.bynn.marketll.module_home.fragment.ProductCommentFragment;
import com.bynn.marketll.module_home.fragment.ProductDetailsFragment;
import com.bynn.marketll.module_home.fragment.ProductFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = HomeRoutePath.PRODUCT_INTRODUCTION_ACTIVITY)
public class ProductIntroductionActivity extends BaseActivity {

    @BindView(R2.id.viewPager)
    ViewPager mViewPager;
    @BindView(R2.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R2.id.rl_header)
    RelativeLayout mRlHeader;
    @BindView(R2.id.divider)
    View mDivider;
    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.iv_share)
    ImageView mIvShare;
    @BindView(R2.id.btn_customer_service)
    Button mBtnCustomerService;
    @BindView(R2.id.btn_shopping_cart)
    Button mBtnShoppingCart;
    @BindView(R2.id.btn_add_shopping_cart)
    Button mBtnAddCart;
    @BindView(R2.id.btn_buy)
    Button mBtnBuy;
    @BindView(R2.id.btn_start_custom)
    Button mBtnCustom;

    // 导航条是否透明，默认非透明
    private boolean mIsHeaderTranslucent;
    private List<String> mTitleList;
    ProductFragment mProductFragment;
    ProductDetailsFragment mDetailsFragment;
    ProductCommentFragment mCommentFragment;
    private FragmentPagerAdapter mAdapter;
    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_product_introduction);
        mUnbinder = ButterKnife.bind(this);
        translucentStatusBar();
        initNotch();
        initView();
    }


    @OnClick(R2.id.iv_back)
    public void onBackClick() {
        finish();
    }

    @OnClick(R2.id.iv_share)
    public void onShareClick() {
        if (mShareDialog == null) {
            mShareDialog = ShareDialog.newInstance();
            mShareDialog.setOnShareClickListener(new ShareDialog.OnShareClickListener() {
                @Override
                public void onClick(ShareDialog.ShareType type) {
                    showToast(type.toString());
                }
            });
        }
        mShareDialog.show(getSupportFragmentManager(), ShareDialog.TAG);
    }

    public void initNotch() {
        int top = QMUIStatusBarHelper.getStatusbarHeight(this);
        if (QMUINotchHelper.hasNotch(this)) {
            top += QMUINotchHelper.getSafeInsetTop(this);
        }
        ((ViewGroup.MarginLayoutParams) mRlHeader.getLayoutParams()).topMargin = top;
    }

    private void initView() {
        if (mTitleList == null) {
            mTitleList = new ArrayList<>();
        }
        mTitleList.add("商品");
        mTitleList.add("详情");
        mTitleList.add("评价");

        mProductFragment = ProductFragment.newInstance();
        mDetailsFragment = ProductDetailsFragment.newInstance();
        mCommentFragment = ProductCommentFragment.newInstance();

        Fragment[] fragments = new Fragment[]{mProductFragment, mDetailsFragment, mCommentFragment};

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
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

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    resetHeaderStyle(true);
                } else {
                    resetHeaderStyle(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // SCROLL_STATE_IDLE ：空闲状态
                // SCROLL_STATE_DRAGGING ：滑动状态
                // SCROLL_STATE_SETTLING ：滑动后滑翔的状态
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mDivider.setVisibility(mViewPager.getCurrentItem() == 0 ? View.GONE : View.VISIBLE);
                } else {
                    mDivider.setVisibility(View.GONE);
                }
            }
        });
        mViewPager.setCurrentItem(0);
        resetHeaderStyle(true);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 重设导航栏的样式
     *
     * @param isTranslucent 是否透明
     */
    public void resetHeaderStyle(boolean isTranslucent) {
        if (mIsHeaderTranslucent == isTranslucent) {
            return;
        }
        if (isTranslucent) {
            mIvBack.setImageResource(R.mipmap.home_ic_nav_back_white_normal);
            mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.basic_white), ContextCompat.getColor(this, R.color.basic_white));
            mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.basic_white));
            mIvShare.setImageResource(R.mipmap.home_ic_nav_share_white_noraml);
            mDivider.setVisibility(View.GONE);
        } else {
            mIvBack.setImageResource(R.mipmap.home_ic_nav_back_gray_normal);
            mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.basic_text_normal), ContextCompat.getColor(this, R.color.basic_text_dark));
            mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.basic_colorAccent));
            mIvShare.setImageResource(R.mipmap.home_ic_nav_share_gray_noraml);
            mDivider.setVisibility(View.VISIBLE);
        }
        mIsHeaderTranslucent = isTranslucent;
    }
}

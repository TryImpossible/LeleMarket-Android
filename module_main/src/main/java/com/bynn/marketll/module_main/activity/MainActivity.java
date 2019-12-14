package com.bynn.marketll.module_main.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bynn.common.base.BaseActivity;
import com.bynn.marketll.module_custom.fragment.CustomFragment;
import com.bynn.marketll.module_discover.fragment.DiscoverFragment;
import com.bynn.marketll.module_home.fragment.HomeFragment;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;
import com.bynn.marketll.module_mine.fragment.MineFragment;
import com.bynn.marketll.module_shopping_cart.fragment.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static final String TAG_HOME          = "home";
    public static final String TAG_DISCOVER      = "discover";
    public static final String TAG_CUSTOMIZATION = "customization";
    public static final String TAG_SHOPPING_CART = "shoppintcart";
    public static final String TAG_MINE          = "mine";

    @BindView(R2.id.nav_view)
    BottomNavigationView mBottomNavView;
    //
    private HomeFragment         mHomeFragment;
    private DiscoverFragment     mDiscoverFragment;
    private CustomFragment       mCustomFragment;
    private ShoppingCartFragment mShoppingCartFragment;
    private MineFragment         mMimeFragment;

    private Fragment mLastFragment;
    private int      mLastSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectNavitionItem(menuItem.getItemId());
                // 表示事件被消息，返回false需要手动menuItem.setCheck()
                return true;
            }
        });
        mBottomNavView.setSelectedItemId(R.id.navigation_home);
    }

    private void selectNavitionItem(int id) {
        if (mLastSelectedId != id) {
            mLastSelectedId = id;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            hideAllFragment(ft);
            showFragment(id, ft);
            ft.commit();
        }
    }

    private void hideAllFragment(FragmentTransaction ft) {
        if (mLastFragment != null && !mLastFragment.isHidden()) {
            ft.hide(mLastFragment);
        }
    }

    private void showFragment(int id, FragmentTransaction ft) {
        if (id == R.id.navigation_home) {
            if (mHomeFragment == null) {
                mHomeFragment = HomeFragment.newInstance();
                ft.add(R.id.container, mHomeFragment, TAG_HOME);
            } else {
                ft.show(mHomeFragment);
            }
            mLastFragment = mHomeFragment;
        }
        if (id == R.id.navigation_discover) {
            if (mDiscoverFragment == null) {
                mDiscoverFragment = DiscoverFragment.newInstance();
                ft.add(R.id.container, mDiscoverFragment, TAG_DISCOVER);
            } else {
                ft.show(mDiscoverFragment);
            }
            mLastFragment = mDiscoverFragment;
        }
        if (id == R.id.navigation_custom) {
            if (mCustomFragment == null) {
                mCustomFragment = CustomFragment.newInstance();
                ft.add(R.id.container, mCustomFragment, TAG_CUSTOMIZATION);
            } else {
                ft.show(mCustomFragment);
            }
            mLastFragment = mCustomFragment;
        }
        if (id == R.id.navigation_shopping_cart) {
            if (mShoppingCartFragment == null) {
                mShoppingCartFragment = ShoppingCartFragment.newInstance();
                ft.add(R.id.container, mShoppingCartFragment, TAG_SHOPPING_CART);
            } else {
                ft.show(mShoppingCartFragment);
            }
            mLastFragment = mShoppingCartFragment;
        }
        if (id == R.id.navigation_mine) {
            if (mMimeFragment == null) {
                mMimeFragment = MineFragment.newInstance();
                ft.add(R.id.container, mMimeFragment, TAG_MINE);
            } else {
                ft.show(mMimeFragment);
            }
            mLastFragment = mMimeFragment;
        }
    }

}

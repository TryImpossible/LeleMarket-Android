package com.bynn.marketll.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.bynn.marketll.R;
import com.bynn.marketll.ui.base.BaseActivity;
import com.bynn.marketll.ui.customization.CustomizationFragment;
import com.bynn.marketll.ui.discover.DiscoverFragment;
import com.bynn.marketll.ui.home.HomeFragment;
import com.bynn.marketll.ui.mine.MineFragment;
import com.bynn.marketll.ui.shoppingcart.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static final String TAG_HOME          = "home";
    public static final String TAG_DISCOVER      = "discover";
    public static final String TAG_CUSTOMIZATION = "customization";
    public static final String TAG_SHOPPING_CART = "shoppintcart";
    public static final String TAG_MINE          = "mine";

    @BindView(R.id.nav_view)
    BottomNavigationView mBottomNavView;

    private HomeFragment          mHomeFragment;
    private DiscoverFragment      mDiscoverFragment;
    private CustomizationFragment mCustomizationFragment;
    private ShoppingCartFragment  mShoppingCartFragment;
    private MineFragment          mMimeFragment;

    private Fragment mLastFragment;
    private int      mLastSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        switch (id) {
            case R.id.navigation_home:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                    ft.add(R.id.container, mHomeFragment, TAG_HOME);
                } else {
                    ft.show(mHomeFragment);
                }
                mLastFragment = mHomeFragment;
                break;
            case R.id.navigation_discover:
                if (mDiscoverFragment == null) {
                    mDiscoverFragment = DiscoverFragment.newInstance();
                    ft.add(R.id.container, mDiscoverFragment, TAG_DISCOVER);
                } else {
                    ft.show(mDiscoverFragment);
                }
                mLastFragment = mDiscoverFragment;
                break;
            case R.id.navigation_customization:
                if (mCustomizationFragment == null) {
                    mCustomizationFragment = CustomizationFragment.newInstance();
                    ft.add(R.id.container, mCustomizationFragment, TAG_CUSTOMIZATION);
                } else {
                    ft.show(mCustomizationFragment);
                }
                mLastFragment = mCustomizationFragment;
                break;
            case R.id.navigation_shopping_cart:
                if (mShoppingCartFragment == null) {
                    mShoppingCartFragment = ShoppingCartFragment.newInstance();
                    ft.add(R.id.container, mShoppingCartFragment, TAG_SHOPPING_CART);
                } else {
                    ft.show(mShoppingCartFragment);
                }
                mLastFragment = mShoppingCartFragment;
                break;
            case R.id.navigation_mine:
                if (mMimeFragment == null) {
                    mMimeFragment = MineFragment.newInstance();
                    ft.add(R.id.container, mMimeFragment, TAG_MINE);
                } else {
                    ft.show(mMimeFragment);
                }
                mLastFragment = mMimeFragment;
                break;
            default:
                break;
        }
    }

}

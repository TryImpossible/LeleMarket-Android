package com.bynn.marketll.module_custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.router.CustomRoutePath;
import com.bynn.common.router.MainRoutePath;
import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.marketll.module_custom.CustomPresenter;
import com.bynn.marketll.module_custom.R;
import com.bynn.marketll.module_custom.R2;
import com.bynn.marketll.module_custom.adapter.MenuAdapter;
import com.bynn.marketll.module_custom.bean.MenuBean;
import com.bynn.marketll.module_custom.bean.MenuResult;
import com.bynn.marketll.module_custom.dagger.CustomComponent;
import com.bynn.marketll.module_custom.dagger.CustomModule;
import com.bynn.marketll.module_custom.dagger.DaggerCustomComponent;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = CustomRoutePath.CUSTOM_FRAGMENT)
public class CustomFragment extends BaseFragment {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.frameLayout)
    FrameLayout  mFrameLayout;

    private MenuAdapter     mMenuAdapter;
    private CustomPresenter mCustomPresenter;
    private Fragment        mLastSelectedFragment;

    public static CustomFragment newInstance() {

        Bundle args = new Bundle();

        CustomFragment fragment = new CustomFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.custom_fragment_customization, container, false);
        ButterKnife.bind(this, view);
        injectPresenter();
        loadData();
        initView();
        return view;
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof MenuResult) {
            MenuResult.DataBean data = ((MenuResult) successObj).getData();
            if (null != data) {
                MenuBean menu = data.getMenus().get(0);
                menu.setSelected(true);
                mMenuAdapter.setNewData(data.getMenus());

                mLastSelectedFragment = GoodsFragment.newInstance(menu.getId(), (ArrayList<BannerBean>) data.getBanners(), (ArrayList<RecommendGoodsBean>) data.getRecommendGoods());
                getChildFragmentManager().beginTransaction().add(R.id.frameLayout, mLastSelectedFragment, String.valueOf(menu.getId()))
                        .commit();
            }
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

    private void injectPresenter() {
        CustomComponent component = DaggerCustomComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .customModule(new CustomModule(this))
                .build();
        mCustomPresenter = component.getCustomPresenter();
        getLifecycle().addObserver(mCustomPresenter);
    }

    private void loadData() {
        mCustomPresenter.getMenus();
    }

    private void initView() {
        mMenuAdapter = new MenuAdapter(new ArrayList<>());
        mMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int lastSelected = mMenuAdapter.getSelectedPosition();
                if (position == lastSelected) {
                    return;
                }
                mMenuAdapter.getItem(lastSelected).setSelected(false);
                mMenuAdapter.notifyItemChanged(lastSelected);

                MenuBean menu = mMenuAdapter.getItem(position);
                menu.setSelected(true);
                mMenuAdapter.notifyItemChanged(position);
                // 切换菜单
                switchFragment(menu.getId());
            }
        });
        mRecyclerView.setAdapter(mMenuAdapter);
    }

    /**
     * 显示、隐藏Fragment
     *
     * @param menuId
     */
    private void switchFragment(int menuId) {

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (null != mLastSelectedFragment && !mLastSelectedFragment.isHidden()) {
            ft.hide(mLastSelectedFragment);
        }
        Fragment fragment = getChildFragmentManager().findFragmentByTag(String.valueOf(menuId));
        if (null == fragment) {
            fragment = GoodsFragment.newInstance(menuId);
            ft.add(R.id.frameLayout, fragment, String.valueOf(menuId));
        } else {
            ft.show(fragment);
        }
        ft.commit();
        mLastSelectedFragment = fragment;
    }
}

package com.bynn.marketll.module_mine.fragment;


import android.content.ClipData;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.MineRoutePath;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.qmui.QMUIDeviceHelper;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.adapter.MineAdapter;
import com.bynn.marketll.module_mine.bean.MineBean;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = MineRoutePath.MINE_FRAGMENT)
public class MineFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private MineAdapter mMineAdapter;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mine_fragment_mine, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    private void initView() {
        translucentStatusBar();
        List<MineBean> dataSource = new ArrayList<>();
        dataSource.add(new MineBean(R.mipmap.mine_ic_coupon, getString(R.string.mine_label_coupon)));
        dataSource.add(new MineBean(R.mipmap.mine_ic_works, getString(R.string.mine_label_works)));
        dataSource.add(new MineBean(R.mipmap.mine_ic_friends, getString(R.string.mine_label_friends)));
        dataSource.add(new MineBean(R.mipmap.mine_ic_address, getString(R.string.mine_label_address)));
        dataSource.add(new MineBean(R.mipmap.mine_ic_customer_service, getString(R.string.mine_label_customer_service)));
        dataSource.add(new MineBean(R.mipmap.mine_ic_scan_qrcode, getString(R.string.mine_label_scan_qrcode)));
        dataSource.add(new MineBean(R.mipmap.mine_ic_see_more, getString(R.string.mine_label_see_more)));

        mMineAdapter = new MineAdapter(dataSource);
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.mine_item_mine_header, null, false);
        mMineAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mMineAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                // 线条左右边距
//                int margin = QMUIDisplayHelper.dp2px(getContext(), 16);
//                // 线条高度
//                int height = QMUIDisplayHelper.dp2px(getContext(), 1);
//                // 间距
//                int space = QMUIDisplayHelper.dp2px(getContext(), 12);
//
//                int position = parent.getChildAdapterPosition(view);
//            }
//        });
    }
}

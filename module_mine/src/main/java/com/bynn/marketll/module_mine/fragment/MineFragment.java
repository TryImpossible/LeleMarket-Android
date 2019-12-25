package com.bynn.marketll.module_mine.fragment;


import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.MineRoutePath;
import com.bynn.common.base.BaseFragment;
import com.bynn.common.qmui.QMUIDeviceHelper;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.adapter.MineAdapter;
import com.bynn.marketll.module_mine.bean.MineBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = MineRoutePath.MINE_FRAGMENT)
public class MineFragment extends BaseFragment implements View.OnClickListener {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wait_pay:
                break;
            case R.id.btn_wait_delivery:
                break;
            case R.id.btn_wait_take_delivery:
                break;
            case R.id.btn_wait_comment:
                break;
            case R.id.btn_all_orders:
                break;
            default:
                break;
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
        mMineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast(mMineAdapter.getItem(position).getTitle());
            }
        });
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.mine_item_mine_header, null, false);
        headerView.findViewById(R.id.btn_wait_pay).setOnClickListener(this);
        headerView.findViewById(R.id.btn_wait_delivery).setOnClickListener(this);
        headerView.findViewById(R.id.btn_wait_take_delivery).setOnClickListener(this);
        headerView.findViewById(R.id.btn_wait_comment).setOnClickListener(this);
        headerView.findViewById(R.id.btn_all_orders).setOnClickListener(this);
        mMineAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mMineAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                // 线条高度
                int lineHeight = QMUIDisplayHelper.dp2px(getContext(), 1);
                // 间距
                int space = QMUIDisplayHelper.dp2px(getContext(), 10);

                int position = parent.getChildAdapterPosition(view);
                if (position == 1 || position == 6) {
                    outRect.top = space;
                }
                if (position == 5 || position == 7) {
                    outRect.bottom = lineHeight;
                }
            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setStyle(Paint.Style.FILL);
                paint.setAntiAlias(false);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.common_divide_line));

                int lineHeight = QMUIDisplayHelper.dp2px(getContext(), 1);
                int count = parent.getChildCount();
                for (int i = 1; i < count; i++) {
                    View child = parent.getChildAt(i);
                    if (i == 1 || i == 6) {
                        c.drawRect(child.getLeft(), child.getTop() - lineHeight, child.getRight(), child.getTop(), paint);
                    }
                    if (i == 5 || i == 7) {
                        c.drawRect(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + lineHeight, paint);
                    }
                }

            }
        });
    }
}

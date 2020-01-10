package com.bynn.marketll.module_mine.fragment;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bynn.common.router.LoginNavigationCallbackImpl;
import com.bynn.common.router.MainRoutePath;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.R2;
import com.bynn.marketll.module_mine.adapter.MineAdapter;
import com.bynn.marketll.module_mine.bean.MineBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = MineRoutePath.MINE_FRAGMENT)
public class MineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_login || id == R.id.iv_default_header) {
            BaseApplication.getARouter().build(MineRoutePath.LOGIN_ACTIVITY).navigation();
        } else {
            if (id == R.id.btn_wait_pay) {

            } else if (id == R.id.btn_wait_delivery) {

            } else if (id == R.id.btn_wait_take_delivery) {

            } else if (id == R.id.btn_wait_comment) {

            } else if (id == R.id.btn_all_orders) {

            }
            BaseApplication.getARouter()
                    .build(MineRoutePath.ORDER_ACTIVITY)
                    .navigation(getActivity(), new LoginNavigationCallbackImpl());
        }
    }

    private void initView() {
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
                switch (position) {
                    case 0:
                        BaseApplication.getARouter()
                                .build(MineRoutePath.MINE_COUPON_ACTIVITY)
                                .navigation(getActivity(), new LoginNavigationCallbackImpl());
                        break;
                    case 1:
                        BaseApplication.getARouter()
                                .build(MineRoutePath.MINE_WORK_ACTIVITY)
                                .navigation(getActivity(), new LoginNavigationCallbackImpl());
                        break;
                    case 2:
                        BaseApplication.getARouter()
                                .build(MineRoutePath.MINE_FRIENDS_ACTIVITY)
                                .navigation(getActivity(), new LoginNavigationCallbackImpl());
                        break;
                    case 3:
                        BaseApplication.getARouter()
                                .build(MineRoutePath.ADDRESS_ACTIVITY)
                                .navigation(getActivity(), new LoginNavigationCallbackImpl());
                        break;
                    case 4:
                        BaseApplication.getARouter()
                                .build(MineRoutePath.CUSTOMER_SERVICE_ACTIVITY)
                                .navigation();
                        break;
                    case 5:
                        BaseApplication.getARouter()
//                                .build(MainRoutePath.SCAN_CODE_ACTIVITY)
                                .build(MineRoutePath.LOAD_STATE_LAYOUT_ACTIVITY)
                                .navigation();
                        break;
                    case 6:
                        BaseApplication.getARouter()
                                .build(MineRoutePath.SEE_MORE_ACTIVITY)
                                .navigation();
                        break;
                    default:
                        break;
                }
            }
        });
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.mine_item_mine_header, null, false);
        headerView.findViewById(R.id.tv_login).setOnClickListener(this);

        ImageView defaultHeader = headerView.findViewById(R.id.iv_default_header);
        Glide.with(this)
                .load(R.mipmap.mine_ic_user_default_header)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(defaultHeader);
        defaultHeader.setOnClickListener(this);

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
                paint.setColor(ContextCompat.getColor(getContext(), R.color.basic_divide_line));

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

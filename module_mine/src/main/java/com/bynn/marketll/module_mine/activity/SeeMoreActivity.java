package com.bynn.marketll.module_mine.activity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.lib_basic.utils.AppUtils;
import com.bynn.lib_basic.view.HeaderView;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.R2;
import com.bynn.marketll.module_mine.adapter.SeeMoreAdapter;
import com.bynn.marketll.module_mine.bean.SeeMoreBean;
import com.bynn.marketll.module_mine.constants.MineConstants;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = MineRoutePath.SEE_MORE_ACTIVITY)
public class SeeMoreActivity extends BaseActivity {

    @BindView(R2.id.headerView)
    HeaderView mHeaderView;
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private SeeMoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_see_more);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeaderView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<SeeMoreBean> data = new ArrayList<>();
        data.add(new SeeMoreBean(getString(R.string.mine_label_push_setting)));
        data.add(new SeeMoreBean(getString(R.string.mine_label_feedback)));
        data.add(new SeeMoreBean(getString(R.string.mine_label_cheer_us)));
        data.add(new SeeMoreBean(getString(R.string.mine_label_about_us)));
        data.add(new SeeMoreBean(getString(R.string.mine_label_business_cooperation)));
        data.add(new SeeMoreBean(getString(R.string.mine_label_copyright_statement)));
        data.add(new SeeMoreBean(getString(R.string.mine_label_clear_cache)));
        mAdapter = new SeeMoreAdapter(data);
        View view = LayoutInflater.from(this).inflate(R.layout.mine_item_see_more_header, null);
        ImageView appIcon = view.findViewById(R.id.iv_app_icon);
        Glide.with(this).
                load(R.mipmap.mine_ic_app_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)).override(130, 130))
                .into(appIcon);
        ((TextView) view.findViewById(R.id.tv_version_name)).setText("V " + AppUtils.getVersionName(this));
        mAdapter.addHeaderView(view);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    AppUtils.startNotificationSetting(SeeMoreActivity.this);
                } else if (position == 3) {
                    BaseApplication.getARouter()
                            .build(MineRoutePath.NORMAL_WEB_ACTIVITY)
                            .withString("url", MineConstants.ABOUT_US)
                            .navigation();
                } else if (position == 4) {
                    BaseApplication.getARouter()
                            .build(MineRoutePath.NORMAL_WEB_ACTIVITY)
                            .withString("url", MineConstants.BUSINESS_COOPERATION)
                            .navigation();
                } else if (position == 5) {
                    BaseApplication.getARouter()
                            .build(MineRoutePath.NORMAL_WEB_ACTIVITY)
                            .withString("url", MineConstants.COPYRIGHT_STATEMENT)
                            .navigation();
                } else if (position == 6) {
                    showToast("清理成功");
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                // 线条高度
                int lineHeight = QMUIDisplayHelper.dp2px(SeeMoreActivity.this, 1);
                // 间距
                int space = QMUIDisplayHelper.dp2px(SeeMoreActivity.this, 10);

                int position = parent.getChildAdapterPosition(view);
                if (position == 1 || position == 4 || position == 7) {
                    outRect.top = lineHeight;
                }
                if (position == 3 || position == 6) {
                    outRect.bottom = space + lineHeight;
                }
                if (position == 7) {
                    outRect.bottom = lineHeight;
                }
            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setStyle(Paint.Style.FILL);
                paint.setAntiAlias(false);
                paint.setColor(ContextCompat.getColor(SeeMoreActivity.this, R.color.basic_divide_line));

                int lineHeight = QMUIDisplayHelper.dp2px(SeeMoreActivity.this, 1);
                int count = parent.getChildCount();
                for (int i = 1; i < count; i++) {
                    View child = parent.getChildAt(i);
                    if (i == 1 || i == 4 || i == 7) {
                        c.drawRect(child.getLeft(), child.getTop() - lineHeight, child.getRight(), child.getTop(), paint);
                    }
                    if (i == 3 || i == 6 || i == 7) {
                        c.drawRect(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + lineHeight, paint);
                    }
                }
            }
        });
    }
}

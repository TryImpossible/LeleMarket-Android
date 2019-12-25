package com.bynn.marketll.module_mine.adapter;

import android.graphics.drawable.Drawable;

import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.bean.MineBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MineAdapter extends BaseQuickAdapter<MineBean, BaseViewHolder> {

    public MineAdapter(@Nullable List<MineBean> data) {
        super(R.layout.mine_item_mine_cell, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineBean item) {
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.common_layout_selector);
//        drawable.setColorFilter();
        helper.setText(R.id.tv_title, item.getTitle())
                .setImageResource(R.id.iv_icon, item.getIcon());
    }
}

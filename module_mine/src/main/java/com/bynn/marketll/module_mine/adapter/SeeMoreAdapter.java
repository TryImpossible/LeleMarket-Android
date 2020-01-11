package com.bynn.marketll.module_mine.adapter;

import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.bean.SeeMoreBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SeeMoreAdapter extends BaseQuickAdapter<SeeMoreBean, BaseViewHolder> {
    public SeeMoreAdapter(@Nullable List<SeeMoreBean> data) {
        super(R.layout.mine_item_see_more_cell, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SeeMoreBean item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent())
                .setGone(R.id.divider, position != 3 && position != 6 && position != 7);
    }
}

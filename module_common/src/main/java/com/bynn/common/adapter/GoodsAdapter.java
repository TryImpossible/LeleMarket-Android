package com.bynn.common.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bynn.common.R;
import com.bynn.common.bean.RecommendGoodsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GoodsAdapter extends BaseQuickAdapter<RecommendGoodsBean, BaseViewHolder> {
    public GoodsAdapter(@Nullable List<RecommendGoodsBean> data) {
        super(R.layout.common_item_goods, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecommendGoodsBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext)
                .load(item.getIcoUrl())
                .into(icon);
        helper.setText(R.id.tv_type, mContext.getString(item.getType() == 1 ? R.string.common_label_ketuyin : R.string.common_label_kekezhi))
                .setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_custom_count, String.format(mContext.getString(R.string.common_label_already_custom_count), item.getSell()))
                .addOnClickListener(R.id.btn_custom);
    }
}

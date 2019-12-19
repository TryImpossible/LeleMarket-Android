package com.bynn.marketll.module_home.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.bean.RecommendGoodsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class NavInfoAdapter extends BaseQuickAdapter<RecommendGoodsBean, BaseViewHolder> {
    public NavInfoAdapter(@Nullable List<RecommendGoodsBean> data) {
        super(R.layout.home_item_nav_info, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecommendGoodsBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext)
                .load(item.getIcoUrl())
                .into(icon);
        helper.setText(R.id.tv_type, mContext.getString(item.getType() == 1 ? R.string.home_label_ketuyin : R.string.home_label_kekezhi))
                .setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_custom_count, String.format(mContext.getString(R.string.home_label_already_custom_count), item.getSell()))
                .addOnClickListener(R.id.btn_custom);
    }
}

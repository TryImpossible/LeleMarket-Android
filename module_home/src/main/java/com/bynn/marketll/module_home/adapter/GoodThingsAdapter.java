package com.bynn.marketll.module_home.adapter;

import android.text.SpannableStringBuilder;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.marketll.module_home.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class GoodThingsAdapter extends BaseQuickAdapter<RecommendGoodsBean, BaseViewHolder> {
    public GoodThingsAdapter(@Nullable List<RecommendGoodsBean> data) {
        super(R.layout.home_item_good_things, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecommendGoodsBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext)
                .load(item.getImgUrl())
                .into(icon);

        SpannableStringBuilder price = new SpanUtils()
                .append(String.format("￥%.1f", item.getNow_price()))
                .setFontSize(15, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.basic_colorAccent))
                .append(String.format("￥%.1f", item.getOrg_price()))
                .setFontSize(12, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.basic_text_light))
                .setStrikethrough()
                .create();
        String sellCount = String.format(mContext.getString(com.bynn.common.R.string.common_label_already_sell_count), item.getSell());
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_price, price)
                .setText(R.id.tv_sell_count, sellCount);
    }
}

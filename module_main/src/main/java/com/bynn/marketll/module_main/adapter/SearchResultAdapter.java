package com.bynn.marketll.module_main.adapter;

import android.text.SpannableStringBuilder;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.marketll.module_main.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SearchResultAdapter extends BaseQuickAdapter<RecommendGoodsBean, BaseViewHolder> {
    public SearchResultAdapter(@Nullable List<RecommendGoodsBean> data) {
        super(R.layout.main_item_serach_result, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecommendGoodsBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getIconUrl()).into(icon);

        String saleCount = String.format(mContext.getString(R.string.common_label_already_custom_count), item.getSell());
        SpannableStringBuilder strBuilder = new SpanUtils().append(saleCount.substring(0, 3))
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.basic_text_dark))
                .append(saleCount.substring(3, saleCount.length() - 1))
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.basic_colorAccent))
                .append(saleCount.substring(saleCount.length() - 1))
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.basic_text_dark))
                .create();

        helper.setText(R.id.tv_title, item.getSaleTitle())
                .setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_sale, strBuilder);


    }
}

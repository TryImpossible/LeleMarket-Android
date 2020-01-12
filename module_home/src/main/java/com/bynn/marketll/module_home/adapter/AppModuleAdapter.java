package com.bynn.marketll.module_home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.bean.AppModuleBean;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

public class AppModuleAdapter extends BaseSectionQuickAdapter<AppModuleBean, BaseViewHolder> {

    public AppModuleAdapter(List<AppModuleBean> data) {
        super(com.bynn.common.R.layout.common_item_goods, R.layout.home_item_app_module_section, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, AppModuleBean item) {
        helper.setText(R.id.tv_name, item.header);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AppModuleBean item) {
        RecommendGoodsBean goodsBean = (RecommendGoodsBean)item.t;
        ImageView icon = helper.getView(com.bynn.common.R.id.iv_icon);
        Glide.with(mContext)
                .load(goodsBean.getIcoUrl())
                .into(icon);
        helper.setText(com.bynn.common.R.id.tv_type, mContext.getString(goodsBean.getType() == 1 ? com.bynn.common.R.string.common_label_ketuyin : com.bynn.common.R.string.common_label_kekezhi))
                .setText(com.bynn.common.R.id.tv_name, goodsBean.getName())
                .setText(com.bynn.common.R.id.tv_custom_count, String.format(mContext.getString(com.bynn.common.R.string.common_label_already_custom_count), goodsBean.getSell()))
                .addOnClickListener(com.bynn.common.R.id.btn_custom);
    }
}

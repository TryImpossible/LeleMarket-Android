package com.bynn.marketll.module_shopping_cart.adapter;

import android.text.SpannableStringBuilder;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.marketll.module_shopping_cart.R;
import com.bynn.marketll.module_shopping_cart.bean.ShopBean;
import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartEntity;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ShoppingCartAdapter extends BaseSectionQuickAdapter<ShoppingCartEntity, BaseViewHolder> {
    public ShoppingCartAdapter(List<ShoppingCartEntity> data) {
        super(R.layout.shopping_item_shopping_cart, R.layout.shopping_item_shopping_cart_section, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ShoppingCartEntity item) {
        helper.setText(R.id.tv_name, item.header);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShoppingCartEntity item) {
        ShopBean bean = (ShopBean) item.t;
        ImageView image = helper.getView(R.id.iv_image);
        Glide.with(mContext)
                .load(bean.getPre_url())
                .into(image);

        String price = String.format("￥%.2f", bean.getNowPrice());
        SpannableStringBuilder priceBuilder = new SpanUtils().append(price.substring(0, 1))
                .setFontSize(11, true)
                .append(price.substring(1, price.lastIndexOf(".")))
                .setFontSize(15, true)
                .append(price.substring(price.lastIndexOf(".")))
                .setFontSize(11, true)
                .create();
        helper.setText(R.id.tv_name, bean.getName())
                .setText(R.id.tv_attribute, bean.getTextureName())
                .setText(R.id.tv_price, priceBuilder)
                .setText(R.id.tv_num, String.valueOf(bean.getNum()))
                .setBackgroundColor(R.id.ll_attribute, ContextCompat.getColor(mContext, bean.isEdit() ? R.color.basic_activity_background : R.color.basic_transparent))
                .setVisible(R.id.iv_arrow_down, bean.isEdit())
                .addOnClickListener(R.id.ll_attribute);
    }

    /**
     * 编辑模式
     * @param isEnabled
     */
    public void enableEditMode(boolean isEnabled) {
        for (int i = 0; i < getData().size(); i++) {
            ShoppingCartEntity entity = getData().get(i);
            if (entity.isHeader) {
                continue;
            }
            ((ShopBean) entity.t).setEdit(isEnabled);
        }
        notifyDataSetChanged();
    }
}

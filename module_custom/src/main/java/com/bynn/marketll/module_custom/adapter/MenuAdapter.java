package com.bynn.marketll.module_custom.adapter;

import com.bynn.marketll.module_custom.R;
import com.bynn.marketll.module_custom.bean.MenuBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MenuAdapter extends BaseQuickAdapter<MenuBean, BaseViewHolder> {

    public MenuAdapter(@Nullable List<MenuBean> data) {
        super(R.layout.custom_item_menu, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MenuBean item) {
        helper.setVisible(R.id.block, item.isSelected())
                .setText(R.id.tv_name, item.getName().substring(1))
                .setTextColor(R.id.tv_name, ContextCompat.getColor(mContext, item.isSelected() ? R.color.basic_colorAccent :R.color.basic_text_light));
    }

    public int getSelectedPosition() {
        int position = 0;
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).isSelected()) {
                position = i;
                break;
            }
        }
        return position;
    }
}

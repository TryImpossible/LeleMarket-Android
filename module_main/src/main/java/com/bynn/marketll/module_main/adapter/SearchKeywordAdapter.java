package com.bynn.marketll.module_main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SearchKeywordAdapter extends BaseQuickAdapter<KeywordBean, BaseViewHolder> {
    public SearchKeywordAdapter(@Nullable List<KeywordBean> data) {
        super(R.layout.main_item_search_keyword, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, KeywordBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.btn_tag_1, item.getKeywords().get(0).getName())
                .setText(R.id.btn_tag_2, item.getKeywords().get(1).getName())
                .setText(R.id.btn_tag_3, item.getKeywords().get(2).getName());
    }
}

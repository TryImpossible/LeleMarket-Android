package com.bynn.marketll.module_main.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.bynn.marketll.module_main.bean.SearchRecordBean;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.internal.FlowLayout;

import java.util.List;

public class SearchRecordAdapter extends BaseSectionMultiItemQuickAdapter<SearchRecordBean, BaseViewHolder> {

    private View.OnClickListener mKeywordClickListener;

    public SearchRecordAdapter(List<SearchRecordBean> data) {
        super(R.layout.main_item_search_record_section, data);
        addItemType(SearchRecordBean.TYPE_HOT, R.layout.main_item_search_record_hot);
        addItemType(SearchRecordBean.TYPE_HOSTORY, R.layout.main_item_search_record_history);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SearchRecordBean item) {
        helper.setText(R.id.tv_title, item.header);
        if (helper.getAdapterPosition() == 1) {
            helper.setGone(R.id.iv_del, true);
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchRecordBean item) {
        int type = helper.getItemViewType();
        if (type == SearchRecordBean.TYPE_HOT) {
            FlowLayout flowLayout = helper.getView(R.id.flowLayout);
            flowLayout.removeAllViews();
            List<KeywordBean> hotList = (List<KeywordBean>) item.t;
            for (int i = 0; i < hotList.size(); i++) {
                int left = QMUIDisplayHelper.dp2px(mContext, 9);
                int top = QMUIDisplayHelper.dp2px(mContext, 3);
                String name = hotList.get(i).getName();

                TextView keyword = new TextView(mContext);
                keyword.setId(i);
                keyword.setTag(name);
                keyword.setPadding(left, top, left, top);
                keyword.setTextSize(12);
                keyword.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_dark));
                keyword.setBackground(ContextCompat.getDrawable(mContext, R.drawable.common_bg_button_solid_gray_radius_20));
                keyword.setText(hotList.get(i).getName());
                keyword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mKeywordClickListener != null) {
                            mKeywordClickListener.onClick(v);
                        }
                    }
                });

                flowLayout.addView(keyword);
            }
        } else if (type == SearchRecordBean.TYPE_HOSTORY) {
            KeywordBean keyword = (KeywordBean) item.t;
            helper.setText(R.id.tv_name, keyword.getName());
        }
    }

    public void setOnKeywordClickListener(View.OnClickListener keywordClickListener) {
        mKeywordClickListener = keywordClickListener;
    }

}

package com.bynn.marketll.module_main.adapter;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.bynn.marketll.module_main.bean.SearchRecordBean;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.internal.FlowLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class SearchRecordAdapter extends BaseSectionMultiItemQuickAdapter<SearchRecordBean, BaseViewHolder> {

    public SearchRecordAdapter(List<SearchRecordBean> data) {
        super(R.layout.main_item_search_record_section, data);
        addItemType(SearchRecordBean.TYPE_HOT, R.layout.main_item_search_record_hot);
        addItemType(SearchRecordBean.TYPE_HOSTORY, R.layout.main_item_search_record_history);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SearchRecordBean item) {
        helper.setText(R.id.tv_title, item.header);
        if (helper.getAdapterPosition() == 2) {
            helper.setGone(R.id.iv_del, true);
        } else if (helper.getAdapterPosition() == 0) {
            helper.setGone(R.id.iv_del, false);
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
                TextView textView = buildTagBtn(hotList.get(i).getName(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                flowLayout.addView(textView);
            }
        } else if (type == SearchRecordBean.TYPE_HOSTORY) {
            KeywordBean keyword = (KeywordBean) item.t;
            helper.setText(R.id.tv_name, keyword.getName());
        }
    }

    private TextView buildTagBtn(String text, View.OnClickListener listener) {
        TextView tv = new TextView(mContext);

        int left = QMUIDisplayHelper.dp2px(mContext, 12);
        int top = QMUIDisplayHelper.dp2px(mContext, 3);
        tv.setPadding(left, top, left, top);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_dark));
        tv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.main_bg_keyword));
        tv.setText(text);
        tv.setOnClickListener(listener);
        return tv;
    }
}

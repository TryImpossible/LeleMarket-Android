package com.bynn.marketll.module_main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.marketll.module_main.bean.SearchRecordBean;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.internal.FlowLayout;

import java.util.List;

public class SearchRecordAdapter extends BaseSectionMultiItemQuickAdapter<SearchRecordBean, BaseViewHolder> {

    private View.OnClickListener mKeywordClickListener;

    public SearchRecordAdapter(List<SearchRecordBean> data, Context context) {
        super(R.layout.main_item_search_record_section, data);
        mContext = context;
        addItemType(SearchRecordBean.TYPE_HOT, R.layout.main_item_search_record_hot);
        addItemType(SearchRecordBean.TYPE_HOSTORY, R.layout.main_item_search_record_history);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SearchRecordBean item) {
        helper.setText(R.id.tv_title, item.header)
                .setVisible(R.id.iv_del, item.header.equals(mContext.getString(R.string.main_label_recent_search)))
                .addOnClickListener(R.id.iv_del);
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
                keyword.setTextColor(ContextCompat.getColor(mContext, R.color.basic_text_dark));
                keyword.setBackground(ContextCompat.getDrawable(mContext, R.drawable.common_bg_button_stroke_gray_radius_20));
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
            String keyword = (String) item.t;
            helper.setText(R.id.tv_name, keyword);
        }
    }

    public void setOnKeywordClickListener(View.OnClickListener keywordClickListener) {
        mKeywordClickListener = keywordClickListener;
    }

    public void addHotData(List<KeywordBean> data) {
        if (data == null) {
            return;
        }
        addData(0, new SearchRecordBean(true, mContext.getString(R.string.main_label_hot_search)));
        addData(1, new SearchRecordBean(data, SearchRecordBean.TYPE_HOT));
    }

    public void addRecentData(List<String> data) {
        if (data == null || data.size() == 0) {
            return;
        }

        // 先删除再添加，不diff
        int startIndex = -1;
        for (int i = 0; i < getData().size(); i++) {
            SearchRecordBean bean = getItem(i);
            if (bean.isHeader && bean.header == mContext.getString(R.string.main_label_recent_search)) {
                startIndex = i;
                break;
            }
        }
        if (startIndex > -1) {
            int endIndex = getData().size() - 1;
            getData().removeAll(getData().subList(startIndex, endIndex + 1));
            notifyDataSetChanged();
        }

        addData(new SearchRecordBean(true, mContext.getString(R.string.main_label_recent_search)));
        for (String keyword : data) {
            addData(new SearchRecordBean(keyword, SearchRecordBean.TYPE_HOSTORY));
        }
    }

    public void removeRecentData() {
        for (int i = getData().size() - 1; i > 0; i--) {
            SearchRecordBean bean = getItem(i);
            getData().remove(bean);
            notifyItemRemoved(i);
            if (bean.isHeader && bean.header == mContext.getString(R.string.main_label_recent_search)) {
                break;
            }
        }
    }
}

package com.bynn.marketll.module_main.bean;

import com.chad.library.adapter.base.entity.SectionMultiEntity;

public class SearchRecordBean extends SectionMultiEntity {
    // 热门搜索
    public static final int TYPE_HOT = 1;
    // 历史搜索
    public static final int TYPE_HOSTORY = 2;

    private int type;

    public SearchRecordBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SearchRecordBean(Object o, int type) {
        super(o);
        this.type = type;
    }

    @Override
    public int getItemType() {
        return this.type;
    }
}

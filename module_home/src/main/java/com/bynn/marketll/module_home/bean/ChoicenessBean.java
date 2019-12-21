package com.bynn.marketll.module_home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChoicenessBean extends SectionMultiEntity<Object> implements MultiItemEntity {

    public static final int BANNER        = 1;
    public static final int MIDNVA        = 2;
    public static final int HANDPICK      = 3;
    public static final int CUSTOMIZATION = 4;

    private int    itemType;
    private Object item;

    public ChoicenessBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ChoicenessBean(int itemType, Object item) {
        super(item);
        this.itemType = itemType;
        this.item = item;
    }

    @Override
    public int getItemType() {
        return this.itemType;
    }
}

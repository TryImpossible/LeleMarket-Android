package com.bynn.marketll.module_home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChoicenessBean extends SectionMultiEntity<ChoicenessBean.Item> implements MultiItemEntity {

    public static final int HANDPICK      = 1;
    public static final int CUSTOMIZATION = 2;

    private int                 itemType;
    private ChoicenessBean.Item item;

    public ChoicenessBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ChoicenessBean(int itemType, Item item) {
        super(item);
        this.itemType = itemType;
        this.item = item;
    }

    @Override
    public int getItemType() {
        return this.itemType;
    }

    @Data
    public static class Item {

        public Item(List<HandpickBean> handpickBeanList) {
            this.handpickBeanList = handpickBeanList;
        }

        public Item(CustomizationBean customizationBean) {
            this.customizationBean = customizationBean;
        }

        private List<HandpickBean> handpickBeanList;
        private CustomizationBean  customizationBean;

        public List<String> getHandPickImageList() {
            List<String> list = new ArrayList<>();
            for (HandpickBean bean : handpickBeanList) {
                list.add(bean.getImgUrl());
            }
            return list;
        }
    }
}

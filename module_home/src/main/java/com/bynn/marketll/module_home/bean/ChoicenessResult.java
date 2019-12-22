package com.bynn.marketll.module_home.bean;

import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.NetworkResult;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChoicenessResult extends NetworkResult {

    private DataBean data;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DataBean {
        private List<HandpickBean>      handpick;
        private List<CustomizationBean> customization;
        private List<MidNavBean>        midNav;
        private List<TopNavBean>        topNav;
        private List<BannerBean>        banners;

        public List<String> getBannerImageList() {
            List<String> list = new ArrayList<>();
            for (BannerBean bean : banners) {
                list.add(bean.getImgUrl());
            }
            return list;
        }

        public List<String> getHandPickImageList() {
            List<String> list = new ArrayList<>();
            for (HandpickBean bean : handpick) {
                list.add(bean.getImgUrl());
            }
            return list;
        }
    }
}

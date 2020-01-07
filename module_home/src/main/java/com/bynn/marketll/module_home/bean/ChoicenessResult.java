package com.bynn.marketll.module_home.bean;

import com.bynn.common.bean.BannerBean;
import com.bynn.lib_basic.network.ResponseResult;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChoicenessResult extends ResponseResult<ChoicenessResult.DataBean> {

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

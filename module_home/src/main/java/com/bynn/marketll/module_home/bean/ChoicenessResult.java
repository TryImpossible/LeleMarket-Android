package com.bynn.marketll.module_home.bean;

import com.bynn.common.bean.BannerBean;
import com.bynn.lib_basic.network.ResponseResult;

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
    }
}

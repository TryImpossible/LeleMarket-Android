package com.bynn.marketll.module_custom.bean;

import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.NetworkResult;
import com.bynn.common.bean.RecommendGoodsBean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuResult extends NetworkResult {
    private DataBean data;

    @Data
    public static class DataBean {
        private List<MenuBean> menus;
        private List<RecommendGoodsBean> recommendGoods;
        private List<BannerBean> banners;
    }
}

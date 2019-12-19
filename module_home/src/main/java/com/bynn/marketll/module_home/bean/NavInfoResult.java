package com.bynn.marketll.module_home.bean;

import com.bynn.common.bean.NetworkResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NavInfoResult extends NetworkResult {
    private DataBean data;

    @Data
    public static class DataBean {
        private List<RecommendGoodsBean> recommendGoods;
        private List<BannerBean>         banners;
    }
}

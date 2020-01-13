package com.bynn.marketll.module_home.bean;

import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;

public class SpecialInfoResult extends ResponseResult<SpecialInfoResult.DataBean> {

    @Data
    public static class DataBean {
        private List<RecommendGoodsBean> recommendGoods;
        private List<BannerBean>         banners;
    }
}

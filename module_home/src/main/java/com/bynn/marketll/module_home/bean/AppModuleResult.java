package com.bynn.marketll.module_home.bean;

import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;

public class AppModuleResult extends ResponseResult<AppModuleResult.DataBean> {

    @Data
    public static class DataBean {
        private ModuleInfoBean moduleInfo;
        private List<RecommendGoodsBean> androidGoods;
        private List<RecommendGoodsBean> iosGoods;
    }
}

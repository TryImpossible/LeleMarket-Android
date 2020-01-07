package com.bynn.marketll.module_custom.bean;

import com.bynn.common.bean.BannerBean;
import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuResult extends ResponseResult<MenuResult.DataBean> {

    @Data
    public static class DataBean {
        private List<MenuBean>           menus;
        private List<RecommendGoodsBean> recommendGoods;
        private List<BannerBean>         banners;
    }
}

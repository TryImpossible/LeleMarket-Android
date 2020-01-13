package com.bynn.marketll.module_home.bean;

import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

public class GoodsTypeResult extends ResponseResult<GoodsTypeResult.DataBean> {

    public static class DataBean {
        List<GoodsTypeBean> types;
    }
}

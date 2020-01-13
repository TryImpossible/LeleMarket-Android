package com.bynn.marketll.module_home.bean;

import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;

public class GoodsTypeResult extends ResponseResult<GoodsTypeResult.DataBean> {

    @Data
    public static class DataBean {
        List<GoodsTypeBean> types;
    }
}

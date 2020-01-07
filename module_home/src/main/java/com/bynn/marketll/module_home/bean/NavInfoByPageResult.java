package com.bynn.marketll.module_home.bean;

import androidx.annotation.NonNull;

import com.bynn.common.bean.RecommendGoodsBean;
import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class NavInfoByPageResult extends ResponseResult<List<RecommendGoodsBean>> {

    public static NavInfoByPageResult build(@NonNull NavInfoResult navInfoResult) {
        NavInfoByPageResult result = new NavInfoByPageResult();
        result.setCode(navInfoResult.getCode());
        result.setMessage(navInfoResult.getMessage());
        result.setData(navInfoResult.getData().getRecommendGoods());
        return result;
    }
}

package com.bynn.common.bean;

import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecommendGoodsResult extends ResponseResult<List<RecommendGoodsBean>> {

}

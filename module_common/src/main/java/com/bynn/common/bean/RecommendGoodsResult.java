package com.bynn.common.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecommendGoodsResult extends NetworkResult {

    List<RecommendGoodsBean> data;
}

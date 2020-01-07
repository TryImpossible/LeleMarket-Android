package com.bynn.marketll.module_discover.bean;

import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommodityResult extends ResponseResult<List<CommodityBean>> {
}

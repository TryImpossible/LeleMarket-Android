package com.bynn.marketll.module_discover.bean;

import com.bynn.common.bean.NetworkResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommodityResult extends NetworkResult {
    private List<CommodityBean> data;
}

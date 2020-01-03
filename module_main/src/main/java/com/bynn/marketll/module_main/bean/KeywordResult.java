package com.bynn.marketll.module_main.bean;

import com.bynn.common.bean.NetworkResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KeywordResult extends NetworkResult {
    private List<KeywordBean> data;
}

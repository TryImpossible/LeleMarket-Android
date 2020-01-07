package com.bynn.marketll.module_main.bean;

import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KeywordResult extends ResponseResult<List<KeywordBean>> {
}

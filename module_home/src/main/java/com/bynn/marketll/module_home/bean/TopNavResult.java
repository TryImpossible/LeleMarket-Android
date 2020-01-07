package com.bynn.marketll.module_home.bean;

import com.bynn.lib_basic.network.ResponseResult;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TopNavResult extends ResponseResult<TopNavResult.DataBean> {

    @Data
    public static class DataBean {
        private List<TopNavBean> topNav;
    }

}

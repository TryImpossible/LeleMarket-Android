package com.bynn.marketll.module_discover;

import com.bynn.marketll.module_discover.bean.CommodityResult;
import com.bynn.marketll.module_discover.network.DiscoverApi;

import io.reactivex.Observable;

public class DiscoverModel {
    private DiscoverApi mApi;

    public DiscoverModel(DiscoverApi mApi) {
        this.mApi = mApi;
    }

    /**
     * 获取发现数据
     *
     * @param page
     * @return
     */
    public Observable<CommodityResult> getFindList(int page) {
        return mApi.findList(page);
    }
}

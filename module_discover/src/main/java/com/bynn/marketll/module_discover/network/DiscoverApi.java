package com.bynn.marketll.module_discover.network;

import com.bynn.marketll.module_discover.bean.CommodityResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiscoverApi {

    /**
     * 发现模块
     *
     * @param page
     * @return
     */
    @GET("/diyMall/commodity/findList.do")
    Observable<CommodityResult> findList(@Query("page") int page);
}

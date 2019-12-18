package com.bynn.marketll.module_home.network;

import com.bynn.marketll.module_home.bean.TopNavResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HomeApi {

    /**
     * 首页滚动导航栏标签
     */
    @GET("/diyMall/index/sortHome.do?type=topNav")
    Observable<TopNavResult> sortHome();

    /**
     * 热门搜索
     */
    @GET("/diyMall/searchGoods/getRecommand.do")
    void getRecommand();

    /**
     * 精选
     */
    @POST("/diyMall/index/homeRevision2.do")
    void homeRevision2();

}

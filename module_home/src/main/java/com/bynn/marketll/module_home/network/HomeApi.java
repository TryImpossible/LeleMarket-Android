package com.bynn.marketll.module_home.network;

import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HomeApi {

    /**
     * 首页滚动导航栏，标签
     */
    @GET("/diyMall/index/sortHome.do?type=topNav")
    void sortHome();

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

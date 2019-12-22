package com.bynn.marketll.module_custom.network;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.marketll.module_custom.bean.MenuResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomApi {

    /**
     * 定制，菜单栏
     * @return
     */
    @GET("/diyMall/homeNav2/menu.do")
    Observable<MenuResult> menu();

    @GET("/diyMall/homeNav2/menu/goods.do")
    Observable<RecommendGoodsResult> menuGoods(@Query("id") int id, @Query("page") int page);
}

package com.bynn.marketll.module_home.network;

import com.bynn.common.bean.GoodsResult;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.marketll.module_home.bean.AppModuleResult;
import com.bynn.marketll.module_home.bean.ChartParamResult;
import com.bynn.marketll.module_home.bean.ChoicenessResult;
import com.bynn.marketll.module_home.bean.GoodsPropertyResult;
import com.bynn.marketll.module_home.bean.GoodsTypeResult;
import com.bynn.marketll.module_home.bean.NavInfoByPageResult;
import com.bynn.marketll.module_home.bean.NavInfoResult;
import com.bynn.marketll.module_home.bean.SpecialInfoResult;
import com.bynn.marketll.module_home.bean.TopNavResult;

import io.reactivex.Observable;
import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeApi {

    /**
     * 首页滚动导航栏标签
     */
    @GET("/diyMall/index/sortHome.do?type=topNav")
    Observable<TopNavResult> sortHome();

    /**
     * 顶部导航，精选Tab
     */
    @POST("/diyMall/index/homeRevision2.do")
    Observable<ChoicenessResult> homeRevision2();

    /**
     * 顶部导航，其它Tab栏，第一页数据
     */
    @POST("/diyMall/index/topNavInfo.do")
    Observable<NavInfoResult> topNavInfo(@Query(value = "id") int id);

    /**
     * 顶部导航，其它Tab栏，分页接口
     */
    @POST("/diyMall/index/topNavInfoByPage.do")
    Observable<NavInfoByPageResult> topNavInfoByPage(@Body FormBody body);

    /**
     * 手机壳定制
     *
     * @param moduleId
     * @param page
     * @return
     */
    @GET("/diyMall/appModule/getById.do")
    Observable<AppModuleResult> getAppModuleById(@Query(value = "moduleId") int moduleId, @Query(value = "page") int page);

    /**
     * 其它定制
     *
     * @param id
     * @param type
     * @return
     */
    @GET("/diyMall/index/specialInfo.do")
    Observable<SpecialInfoResult> getSpecialInfo(@Query(value = "id") int id, @Query(value = "type") int type);

    /**
     * 发现好物，获取好物类型
     *
     * @return
     */
    @POST("/diyMall/findGoods/load.do")
    Observable<GoodsTypeResult> getGoodsType();

    /**
     * 发现好物，根据类型获取物品数据
     *
     * @param id
     * @param page
     * @return
     */
    @POST("/diyMall/findGoods/getGoods.do")
    Observable<RecommendGoodsResult> getGoods(@Query(value = "id") int id, @Query(value = "page") int page);

    /**
     * 获取指定的商品
     *
     * @param id
     * @param type
     * @param userId
     * @return
     */
    @POST("/diyMall/commodity/goods.do")
    Observable<GoodsResult> getPointGoods(@Query(value = "id") int id, @Query(value = "type") int type, @Query(value = "userId") int userId);

    /**
     * 产品Banner、分享标题和分享logo、商品H5、详情H5、评价H5
     *
     * @param type
     * @param id
     * @return
     */
    @GET("/diyMall/commodity/chartParam2/{type}/{id}.do")
    Observable<ChartParamResult> chartParam2(@Path("type") int type, @Path("id") int id);

    /**
     * 产品属性，规格
     *
     * @param id
     * @return
     */
    @GET("/diyMall/commodity/goodsProperty/1/{id}.do")
    Observable<GoodsPropertyResult> goodsProperty(@Path("id") int id);
}

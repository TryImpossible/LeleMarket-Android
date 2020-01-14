package com.bynn.marketll.module_home;

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
import com.bynn.marketll.module_home.network.HomeApi;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import okhttp3.FormBody;

public class HomeModel {
    private HomeApi mApi;

    public HomeModel(HomeApi api) {
        this.mApi = api;
    }

    /**
     * 获取首页滚动导航栏标签
     */
    public Observable<TopNavResult> getTopNav() {
        return mApi.sortHome();
    }

    /**
     * 获取精选Tab
     *
     * @return
     */
    public Observable<ChoicenessResult> getHomeChoiceness() {
        return mApi.homeRevision2();
    }

    /**
     * 获取其它Tab
     *
     * @return
     */
    public Observable<NavInfoResult> getHomeNavInfo(@NonNull int id) {
//        FormBody params = new FormBody.Builder()
//                .add("id", String.valueOf(id))
//                .build();
        return mApi.topNavInfo(id);
    }

    /**
     * 获取其它Tab，分页接口
     *
     * @return
     */
    public Observable<NavInfoByPageResult> getHomeNavInfoByPage(@NonNull int id, int page) {
        FormBody params = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("page", String.valueOf(page))
                .build();
        return mApi.topNavInfoByPage(params);
    }

    /**
     * 获取手机壳定制
     *
     * @param moduleId
     * @param page
     * @return
     */
    public Observable<AppModuleResult> getAppModuleById(int moduleId, int page) {
        return mApi.getAppModuleById(moduleId, page);
    }

    /**
     * 其它定制
     *
     * @param id
     * @param type
     * @return
     */
    public Observable<SpecialInfoResult> getSpecialInfo(int id, int type) {
        return mApi.getSpecialInfo(id, type);
    }

    /**
     * 发现好物，获取好物类型
     *
     * @return
     */
    public Observable<GoodsTypeResult> getGoodsType() {
        return mApi.getGoodsType();
    }

    /**
     * 发现好物，根据类型获取物品数据
     *
     * @param id
     * @param page
     * @return
     */
    public Observable<RecommendGoodsResult> getGoods(int id, int page) {
        return mApi.getGoods(id, page);
    }

    /**
     * 获取指定的商品
     *
     * @param id
     * @param type
     * @param userId
     * @return
     */
    public Observable<GoodsResult> getPointGood(int id, int type, int userId) {
        return mApi.getPointGoods(id, type, userId);
    }

    /**
     * 产品Banner、分享标题和分享logo、商品H5、详情H5、评价H5
     *
     * @param type
     * @param id
     * @return
     */
    public Observable<ChartParamResult> chartParam2(int type, int id) {
        return mApi.chartParam2(type, id);
    }

    /**
     * 产品属性，规格
     *
     * @param id
     * @return
     */
    public Observable<GoodsPropertyResult> goodsProperty(int id) {
        return mApi.goodsProperty(id);
    }
}

package com.bynn.marketll.module_home;

import com.bynn.marketll.module_home.bean.TopNavResult;
import com.bynn.marketll.module_home.network.HomeApi;

import io.reactivex.Observable;

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
}

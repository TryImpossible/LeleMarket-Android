package com.bynn.marketll.module_home;

import androidx.annotation.NonNull;

import com.bynn.marketll.module_home.bean.ChoicenessResult;
import com.bynn.marketll.module_home.bean.NavInfoByPageResult;
import com.bynn.marketll.module_home.bean.NavInfoResult;
import com.bynn.marketll.module_home.bean.TopNavResult;
import com.bynn.marketll.module_home.network.HomeApi;

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
}

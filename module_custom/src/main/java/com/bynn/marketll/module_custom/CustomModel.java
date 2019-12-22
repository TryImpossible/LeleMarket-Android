package com.bynn.marketll.module_custom;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.marketll.module_custom.bean.MenuResult;
import com.bynn.marketll.module_custom.network.CustomApi;

import io.reactivex.Observable;

public class CustomModel {
    private CustomApi mApi;

    public CustomModel(CustomApi mApi) {
        this.mApi = mApi;
    }

    /**
     * 获取菜单
     *
     * @return
     */
    public Observable<MenuResult> getMenus() {
        return mApi.menu();
    }

    /**
     * 获取菜单对应的商品
     * @param id 菜单id
     * @param page
     * @return
     */
    public Observable<RecommendGoodsResult> getMenuGoods(int id, int page) {
        return mApi.menuGoods(id, page);
    }
}

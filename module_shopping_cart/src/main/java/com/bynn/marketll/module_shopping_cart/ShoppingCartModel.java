package com.bynn.marketll.module_shopping_cart;

import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartResult;
import com.bynn.marketll.module_shopping_cart.network.ShoppingCartApi;

import io.reactivex.Observable;

public class ShoppingCartModel {
    private ShoppingCartApi mApi;

    public ShoppingCartModel(ShoppingCartApi mApi) {
        this.mApi = mApi;
    }

    /**
     * 购物车列表
     *
     * @param userId
     * @return
     */
    public Observable<ShoppingCartResult> getShoppingCartList(int userId) {
        return mApi.shopListForId(userId);
    }
}

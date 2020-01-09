package com.bynn.marketll.module_shopping_cart.network;

import com.bynn.common.constants.NetApiConstants;
import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShoppingCartApi {
    /**
     * 购物车列表
     *
     * @param userId
     * @return
     */
    @GET(NetApiConstants.SHOP_LIST_FOR_ID)
    Observable<ShoppingCartResult> shopListForId(@Query(value = "userId") int userId);
}

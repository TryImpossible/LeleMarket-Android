package com.bynn.marketll.module_main.network;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.constants.NetApiConstants;
import com.bynn.marketll.module_main.bean.KeywordResult;

import io.reactivex.Observable;
import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainApi {

    /**
     * 热门搜索
     */
    @GET(NetApiConstants.GET_RECOMMAND)
    Observable<KeywordResult> getRecommand();

    /**
     * 搜索关键字
     *
     * @return
     */
    @POST("/diyMall/searchGoods/getKeyword.do")
    Observable<KeywordResult> getKeyword(@Body FormBody body);

    /**
     * 搜索结果
     *
     * @return
     */
    @POST("/diyMall/searchGoods/getGoodsInfo.do")
    Observable<RecommendGoodsResult> getGoodsInfo(@Body FormBody body);
}

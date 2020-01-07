package com.bynn.marketll.module_main;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.constants.NetApiConstants;
import com.bynn.lib_basic.database.HttpDao;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.marketll.module_main.database.HistorySearchDao;
import com.bynn.marketll.module_main.network.MainApi;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

public class MainModel {
    private MainApi mApi;

    public MainModel(MainApi api) {
        this.mApi = api;
    }

    /**
     * 获取热门推荐
     *
     * @return
     */
    public Observable<KeywordResult> getRecommand() {
        return mApi.getRecommand()
                .doOnNext(new Consumer<KeywordResult>() {
                    @Override
                    public void accept(KeywordResult keywordResponse) throws Exception {
                        if (keywordResponse.isSuccess()) {
                            HttpDao.setCache(NetApiConstants.GET_RECOMMAND, keywordResponse);
                        }
                    }
                });
    }

    /**
     * 获取关键字
     *
     * @param name
     * @return
     */
    public Observable<KeywordResult> getKeyword(String name) {
        FormBody params = new FormBody.Builder()
                .add("name", name)
                .build();
        return mApi.getKeyword(params);
    }

    /**
     * 搜索结果
     *
     * @param page
     * @param name
     * @return
     */
    public Observable<RecommendGoodsResult> getGoodsInfo(int page, String name) {
        HistorySearchDao.insertOrUpdate(name);
        FormBody params = new FormBody.Builder()
                .add("userId", "9956133")
                .add("page", String.valueOf(page))
                .add("name", name)
                .build();
        return mApi.getGoodsInfo(params)
                .doOnNext(new Consumer<RecommendGoodsResult>() {
                    @Override
                    public void accept(RecommendGoodsResult recommendGoodsResult) throws Exception {
                        if (recommendGoodsResult.isSuccess()) {

                        }
                    }
                });
    }
}

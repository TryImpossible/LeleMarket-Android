package com.bynn.marketll.module_main;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.constants.NetApiConstants;
import com.bynn.lib_basic.interfaces.ICallback;
import com.bynn.lib_basic.utils.RxJavaUtils;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.marketll.module_main.network.MainApi;
import com.bynn.module_database.RecentSearchDao;
import com.bynn.module_database.HttpDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

public class MainModel {
    private MainApi mApi;

    public MainModel(MainApi api) {
        this.mApi = api;
    }

    public Observable<KeywordResult> getCacheRecommand() {
        KeywordResult result = HttpDao.getCache(NetApiConstants.GET_RECOMMAND, KeywordResult.class);
        return Observable.create(new ObservableOnSubscribe<KeywordResult>() {
            @Override
            public void subscribe(ObservableEmitter<KeywordResult> emitter) throws Exception {

            }
        });
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
     * 获取最近搜索
     */
    public Observable<List<String>> getRecentSearch() {
        return RecentSearchDao.findAll();
    }

    /**
     * 删除最近搜索
     */
    public Observable<Boolean> delRecentSearch() {
        return RecentSearchDao.deleteAll();
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
        RecentSearchDao.insertOrUpdate(name);
        FormBody params = new FormBody.Builder()
                .add("userId", "9956133")
                .add("page", String.valueOf(page))
                .add("name", name)
                .build();
        return mApi.getGoodsInfo(params);
    }
}

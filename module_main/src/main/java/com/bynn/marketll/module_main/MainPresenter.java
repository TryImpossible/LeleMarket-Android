package com.bynn.marketll.module_main;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.interfaces.ICallback;
import com.bynn.lib_basic.network.ResponseObserver;
import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.utils.RxJavaUtils;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.module_database.RecentSearchDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private MainModel mMainModel;

    @Inject
    public MainPresenter(IBaseView iBaseView, MainModel mainModel) {
        this.mIBaseView = iBaseView;
        this.mMainModel = mainModel;
    }

    /**
     * 获取热门推荐
     */
    public void getRecommand() {
        Observable.mergeDelayError(mMainModel.getCacheRecommand(), mMainModel.getRecommand())
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<KeywordResult>() {
                    @Override
                    public void onSuccess(KeywordResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 获取最近搜索
     */
    public void getRecentSearch() {
        mMainModel.getRecentSearch()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mIBaseView.onSuccess(strings);
                    }
                });
    }

    /**
     * 删除最近搜索
     */
    public void delRecentSearch(ICallback callback) {
        mMainModel.delRecentSearch()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (callback != null) {
                            callback.onSuccess(aBoolean);
                        } else {
                            callback.onFailure(new Exception(String.valueOf(aBoolean)));
                        }
                    }
                });
    }

    /**
     * 获取关键字
     *
     * @param name
     */
    public void getKeyword(String name) {
        mMainModel.getKeyword(name)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<KeywordResult>() {
                    @Override
                    public void onSuccess(KeywordResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 搜索结果
     *
     * @param page 页码
     * @param name 关键字
     */
    public void getGoodsInfo(int page, String name) {
        mMainModel.getGoodsInfo(page, name)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<RecommendGoodsResult>() {
                    @Override
                    public void onSuccess(RecommendGoodsResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }
}
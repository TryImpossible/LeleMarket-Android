package com.bynn.marketll.module_main;

import com.bynn.common.base.BasePresenter;
import com.bynn.common.base.IBaseView;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.exception.NetworkResultException;
import com.bynn.marketll.module_main.bean.KeywordResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        mMainModel.getRecommand()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KeywordResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(KeywordResult keywordResult) {
                        if (keywordResult.isSuccess()) {
                            mIBaseView.onSuccess(keywordResult);
                        } else {
                            mIBaseView.onFailure(new NetworkResultException(keywordResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBaseView.onError(e);
                    }

                    @Override
                    public void onComplete() {

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KeywordResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(KeywordResult keywordResult) {
                        if (keywordResult.isSuccess()) {
                            mIBaseView.onSuccess(keywordResult);
                        } else {
                            mIBaseView.onFailure(new NetworkResultException(keywordResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBaseView.onError(e);
                    }

                    @Override
                    public void onComplete() {

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendGoodsResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(RecommendGoodsResult recommendGoodsResult) {
                        if (recommendGoodsResult.isSuccess()) {
                            mIBaseView.onSuccess(recommendGoodsResult);
                        } else {
                            mIBaseView.onFailure(new NetworkResultException(recommendGoodsResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBaseView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
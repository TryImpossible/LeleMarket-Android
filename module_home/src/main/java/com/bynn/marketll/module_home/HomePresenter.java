package com.bynn.marketll.module_home;

import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.lib_basic.network.ResponseResult;
import com.bynn.marketll.module_home.bean.ChoicenessResult;
import com.bynn.marketll.module_home.bean.NavInfoByPageResult;
import com.bynn.marketll.module_home.bean.NavInfoResult;
import com.bynn.marketll.module_home.bean.TopNavResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private HomeModel mHomeModel;

    @Inject
    public HomePresenter(IBaseView iBaseView, HomeModel homeModel) {
        this.mIBaseView = iBaseView;
        this.mHomeModel = homeModel;
    }

    /**
     * 获取首页滚动导航栏标签
     */
    public void getTopNav() {
        mHomeModel.getTopNav()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopNavResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(TopNavResult topNavResult) {
                        if (topNavResult.isSuccess()) {
                            mIBaseView.onSuccess(topNavResult);
                        } else {
                            mIBaseView.onFailure(new ResponseException(topNavResult));
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
     * 获取精选Tab
     *
     * @return
     */
    public void getHomeChoiceness() {
        mHomeModel.getHomeChoiceness()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChoicenessResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ChoicenessResult choicenessResult) {
                        if (choicenessResult.isSuccess()) {
                            mIBaseView.onSuccess(choicenessResult);
                        } else {
                            mIBaseView.onFailure(new ResponseException(choicenessResult));
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
     * 获取其它Tab
     *
     * @param id 类型
     */
    public void getHomeNavInfoByPage(int id, int page) {
        Observable observable = null;
        if (page == 0) {
            observable = mHomeModel.getHomeNavInfo(id);
        } else {
            observable = mHomeModel.getHomeNavInfoByPage(id, page);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Object result) {
                        ResponseResult networkResult = (ResponseResult) result;
                        if (networkResult.isSuccess()) {
                            if (result instanceof NavInfoResult) {
                                mIBaseView.onSuccess(NavInfoByPageResult.build((NavInfoResult) result));
                            } else {
                                mIBaseView.onSuccess((NavInfoByPageResult) result);
                            }
                        } else {
                            mIBaseView.onFailure(new ResponseException(networkResult));
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
package com.bynn.marketll.module_home;

import com.bynn.common.base.BasePresenter;
import com.bynn.common.base.IBaseView;
import com.bynn.common.exception.NetworkResultException;
import com.bynn.marketll.module_home.bean.TopNavResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter {

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
                            mIBaseView.onFailure(new NetworkResultException(topNavResult));
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
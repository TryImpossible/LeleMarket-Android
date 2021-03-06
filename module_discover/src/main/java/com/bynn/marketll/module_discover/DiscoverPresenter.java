package com.bynn.marketll.module_discover;

import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.marketll.module_discover.bean.CommodityResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DiscoverPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private IBaseView     mIBaseView;
    private DiscoverModel mCustomModel;

    @Inject
    public DiscoverPresenter(IBaseView mIBaseView, DiscoverModel mCustomModel) {
        this.mIBaseView = mIBaseView;
        this.mCustomModel = mCustomModel;
    }

    /**
     * 获取发现数据
     *
     * @param page
     */
    public void getFindList(int page) {
        mIBaseView.showLoading();
        mCustomModel.getFindList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommodityResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommodityResult commodityResult) {
                        if (commodityResult.isSuccess()) {
                            mIBaseView.onSuccess(commodityResult);
                        } else {
                            mIBaseView.onFailure(new ResponseException(commodityResult));
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

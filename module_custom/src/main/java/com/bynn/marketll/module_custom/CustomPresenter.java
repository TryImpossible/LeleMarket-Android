package com.bynn.marketll.module_custom;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.marketll.module_custom.bean.MenuResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CustomPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private IBaseView mIBaseView;
    private CustomModel mCustomModel;

    @Inject
    public CustomPresenter(IBaseView mIBaseView, CustomModel mCustomModel) {
        this.mIBaseView = mIBaseView;
        this.mCustomModel = mCustomModel;
    }

    /**
     * 获取菜单
     */
    public void getMenus() {
        mCustomModel.getMenus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MenuResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MenuResult menuResult) {
                        if (menuResult.isSuccess()) {
                            mIBaseView.onSuccess(menuResult);
                        } else {
                            mIBaseView.onFailure(new ResponseException(menuResult));
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
     * 获取菜单对应的商品
     * @param id 菜单id
     * @param page
     * @return
     */
    public void getMenuGoods(int id, int page) {
       mCustomModel.getMenuGoods(id, page)
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
                            mIBaseView.onFailure(new ResponseException(recommendGoodsResult));
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

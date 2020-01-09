package com.bynn.marketll.module_shopping_cart;

import com.bynn.lib_basic.network.ResponseObserver;
import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.utils.RxJavaUtils;
import com.bynn.marketll.module_shopping_cart.bean.ShoppingCartResult;

import javax.inject.Inject;

import io.reactivex.Observer;

public class ShoppingCartPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private IBaseView         mIBaseView;
    private ShoppingCartModel mCartModel;

    @Inject
    public ShoppingCartPresenter(IBaseView mIBaseView, ShoppingCartModel mCustomModel) {
        this.mIBaseView = mIBaseView;
        this.mCartModel = mCustomModel;
    }

    /**
     * 购物车列表
     */
    public void getShoppingCartList(int userId) {
        mCartModel.getShoppingCartList(userId)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<ShoppingCartResult>() {
                    @Override
                    public void onSuccess(ShoppingCartResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }
}

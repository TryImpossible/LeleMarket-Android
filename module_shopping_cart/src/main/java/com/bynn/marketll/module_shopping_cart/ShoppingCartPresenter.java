package com.bynn.marketll.module_shopping_cart;

import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;

import javax.inject.Inject;

public class ShoppingCartPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private IBaseView         mIBaseView;
    private ShoppingCartModel mCustomModel;

    @Inject
    public ShoppingCartPresenter(IBaseView mIBaseView, ShoppingCartModel mCustomModel) {
        this.mIBaseView = mIBaseView;
        this.mCustomModel = mCustomModel;
    }
}

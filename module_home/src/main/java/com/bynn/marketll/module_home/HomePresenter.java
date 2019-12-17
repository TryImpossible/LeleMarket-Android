package com.bynn.marketll.module_home;

import com.bynn.common.base.BasePresenter;
import com.bynn.common.base.IBaseView;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter {

    private HomeModel mHomeModel;

    @Inject
    public HomePresenter(IBaseView iBaseView, HomeModel homeModel) {
        this.mIBaseView = iBaseView;
        this.mHomeModel = homeModel;
    }


}
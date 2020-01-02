package com.bynn.marketll.module_discover;

import com.bynn.common.base.BasePresenter;
import com.bynn.common.base.IBaseView;

import javax.inject.Inject;

public class DiscoverPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private IBaseView   mIBaseView;
    private DiscoverModel mCustomModel;

    @Inject
    public DiscoverPresenter(IBaseView mIBaseView, DiscoverModel mCustomModel) {
        this.mIBaseView = mIBaseView;
        this.mCustomModel = mCustomModel;
    }
}

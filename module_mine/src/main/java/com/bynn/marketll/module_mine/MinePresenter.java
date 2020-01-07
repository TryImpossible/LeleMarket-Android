package com.bynn.marketll.module_mine;

import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;

import javax.inject.Inject;

public class MinePresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private MineModel mMineModel;

    @Inject
    public MinePresenter(IBaseView IBaseView, MineModel mineModel) {
        this.mIBaseView = IBaseView;
        this.mMineModel = mineModel;
    }
}

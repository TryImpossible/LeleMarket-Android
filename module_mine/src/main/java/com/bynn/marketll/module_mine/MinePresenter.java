package com.bynn.marketll.module_mine;

import com.bynn.common.base.BasePresenter;
import com.bynn.common.base.IBaseView;

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

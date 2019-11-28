package com.bynn.marketll.ui;

public interface IBaseView {
    /**
     * 显示进度条
     */
    void showProgress();
    void showProgress(int resId);

    /**
     * 隐藏进度条
     */
    void hideProgress();


}

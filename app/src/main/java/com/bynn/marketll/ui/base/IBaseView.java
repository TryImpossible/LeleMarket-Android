package com.bynn.marketll.ui.base;

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

    /**
     * 显示Toast
     *
     * @param text
     */
    void showToast(String text);

    void showToast(int resId);
}

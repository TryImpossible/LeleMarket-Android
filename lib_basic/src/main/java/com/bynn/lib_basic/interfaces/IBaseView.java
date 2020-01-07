package com.bynn.lib_basic.interfaces;

import com.bynn.lib_basic.network.ResponseException;

public interface IBaseView {
    /**
     * 显示Loading
     */
    default void showLoading() {
    }

    default void showLoading(int resId) {
    }

    /**
     * 隐藏Loading
     */
    default void hideLoading() {
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    default void showToast(String text) {
    }

    default void showToast(int resId) {
    }

    /**
     * 数据请求成功
     */
    void onSuccess(Object successObj);

    /**
     * 数据请求失败
     */
    void onFailure(Throwable e);

    /**
     * 数据请求错误
     * 出现无法联网、缺少权限，内存泄露原因导致无法连接到请求数据源
     */
    default void onError(Throwable e) {
    }

    /**
     * 数据请求完成
     */
    default void onFinish() {
    }
}

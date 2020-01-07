package com.bynn.lib_basic.interfaces;

public interface ICallback {

    /**
     * 数据请求成功
     */
    void onSuccess(Object successObj);

    /**
     * 数据请求失败
     */
    void onFailure(Throwable e);
}

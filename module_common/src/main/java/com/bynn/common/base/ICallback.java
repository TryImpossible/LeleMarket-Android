package com.bynn.common.base;

public interface ICallback {

    /**
     * 数据请求成功
     */
    void onSuccess(Object successObj);

    /**
     * 数据请求失败
     * 请求数据失败，指在请求网络API接口请求方式时，出现无法联网、缺少权限，内存泄露原因导致无法连接到请求数据源
     */
    void onError(Throwable e);
}

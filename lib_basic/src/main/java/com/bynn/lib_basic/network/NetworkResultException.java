package com.bynn.lib_basic.network;

import androidx.annotation.Nullable;

import com.bynn.lib_basic.network.ResponseResult;

/**
 * 网络请求结果异常
 * 1.0版本使用
 */
@Deprecated
public class NetworkResultException extends RuntimeException {
    // 异常状态
    private int    errorCode;
    // 异常信息
    private String errorMessage;
    // api名称
    private String apiName;

    public NetworkResultException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public NetworkResultException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public NetworkResultException(int errorCode, String errorMessage, String apiName) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.apiName = apiName;
    }

    public NetworkResultException(ResponseResult response) {
        if (null != response) {
            this.errorCode = response.getCode();
            this.errorMessage = response.getMessage();
        }
    }

    public NetworkResultException(ResponseResult response, String apiName) {
        if (null != response) {
            this.errorCode = response.getCode();
            this.errorMessage = response.getMessage();
        }
        this.apiName = apiName;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    @Nullable
    @Override
    public String getMessage() {
        if (null != errorMessage) {
            return errorMessage;
        } else {
            return String.valueOf(errorCode);
        }
    }
}

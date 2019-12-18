package com.bynn.common.exception;

import androidx.annotation.Nullable;

import com.bynn.common.bean.NetworkResult;

/**
 * 网络请求结果异常
 */
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

    public NetworkResultException(NetworkResult result) {
        if (null != result) {
            this.errorCode = result.getCode();
            this.errorMessage = result.getMessage();
        }
    }

    public NetworkResultException(NetworkResult result, String apiName) {
        if (null != result) {
            this.errorCode = result.getCode();
            this.errorMessage = result.getMessage();
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

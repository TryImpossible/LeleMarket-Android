package com.bynn.common.bean;

/**
 * 网络请求返回结果基类
 */
public class NetworkResult {
    private int    code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 是否请求成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 200;
    }
}

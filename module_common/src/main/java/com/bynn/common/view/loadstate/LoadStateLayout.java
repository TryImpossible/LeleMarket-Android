package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bynn.common.R;
import com.bynn.common.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadStateLayout extends FrameLayout {
    /**
     * 加载中
     */
    public static final int     LOADING             = 1;
    /**
     * 加载成功
     */
    public static final int     SUCCESS             = 2;
    /**
     * 加载失败，重试
     */
    public static final int     FAILURE             = 3;
    /**
     * 数据为空
     */
    public static final int     EMPTY               = 4;
    /**
     * 无网络
     */
    public static final int     NO_NETWORK          = 5;
    /**
     * 上下文
     */
    private             Context mContext;
    /**
     * 加载视图布局ID
     */
    private             int     mLoadingViewResId   = -1;
    /**
     * 加载视图
     */
    private             View    mLoadingView;
    /**
     * 错误视图布局ID
     */
    private             int     mFailureViewResId   = -1;
    /**
     * 错误视图
     */
    private             View    mFailureView;
    /**
     * 空视图布局ID
     */
    private             int     mEmptyViewResId     = -1;
    /**
     * 空视图
     */
    private             View    mEmptyView;
    /**
     * 无网络视图布局ID
     */
    private             int     mNoNetworkViewResId = -1;
    /**
     * 无网络视图
     */
    private             View    mNoNetworkView;
    /**
     * 上次显示的View，默认null
     */
    private             View    mLastShowView;

    public LoadStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonLoadStateLayout, defStyleAttr, 0);
        if (typedArray != null) {
            mLoadingViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_loading_view);
            mFailureViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_failure_view);
            mEmptyViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_empty_view);
            mNoNetworkViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_no_network_view);
        }
        typedArray.recycle();
    }

    /**
     * 显示加载视图
     */
    public void showLoading() {
        if (mLastShowView != null) {
            mLastShowView.setVisibility(GONE);
        }
        if (mLoadingViewResId != -1 && mLoadingView == null) {
            mLoadingView = LayoutInflater.from(mContext).inflate(mLoadingViewResId, null);
            addView(mLoadingView);
        } else {
            mLoadingView.setVisibility(VISIBLE);
        }
        mLastShowView = mLoadingView;
    }

    /**
     * 显示失败视图
     */
    public void showFailure() {
        if (mLastShowView != null) {
            mLastShowView.setVisibility(GONE);
        }
        if (mFailureViewResId != -1 && mFailureView == null) {
            mFailureView = LayoutInflater.from(mContext).inflate(mFailureViewResId, null);
            addView(mFailureView);
        } else {
            mFailureView.setVisibility(VISIBLE);
        }
        mLastShowView = mFailureView;
    }

    /**
     * 显示空视图
     */
    public void showEmpty() {
        if (mLastShowView != null) {
            mLastShowView.setVisibility(GONE);
        }
        if (mEmptyViewResId != -1 && mEmptyView == null) {
            mEmptyView = LayoutInflater.from(mContext).inflate(mEmptyViewResId, null);
            addView(mEmptyView);
        } else {
            mEmptyView.setVisibility(VISIBLE);
        }
        mLastShowView = mEmptyView;
    }

    /**
     * 显示无网络视图
     */
    public void showNoNetwork() {
        if (mLastShowView != null) {
            mLastShowView.setVisibility(GONE);
        }
        if (mNoNetworkViewResId != -1 && mNoNetworkView == null) {
            mNoNetworkView = LayoutInflater.from(mContext).inflate(mNoNetworkViewResId, null);
            addView(mNoNetworkView);
        } else {
            mNoNetworkView.setVisibility(VISIBLE);
        }
        mLastShowView = mNoNetworkView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
    }

    public View getFailureView() {
        return mFailureView;
    }

    public void setFailureView(View failureView) {
        mFailureView = failureView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public View getNoNetworkView() {
        return mNoNetworkView;
    }

    public void setNoNetworkView(View noNetworkView) {
        mNoNetworkView = noNetworkView;
    }
}

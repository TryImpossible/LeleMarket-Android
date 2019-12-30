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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadStateLayout extends FrameLayout {
    /**
     * 加载中
     */
    public static final int LOADING = 1;
    /**
     * 加载成功
     */
    public static final int SUCCESS = 2;
    /**
     * 加载失败，重试
     */
    public static final int FAILURE = 3;
    /**
     * 数据为空
     */
    public static final int EMPTY = 4;
    /**
     * 无网络
     */
    public static final int NO_NETWORK = 5;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 加载视图
     */
    private View mLoadingView;
    /**
     * 错误视图
     */
    private View mFailureView;
    /**
     * 空视图
     */
    private View mEmptyView;
    /**
     * 无网络视图
     */
    private View mNoNetworkView;

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
            int loadingViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_loading_view);
            mLoadingView = LayoutInflater.from(mContext).inflate(loadingViewResId, this);

            int failureViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_failure_view);
            mFailureView = LayoutInflater.from(mContext).inflate(failureViewResId, this);

            int emptyViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_empty_view);
            mEmptyView = LayoutInflater.from(mContext).inflate(emptyViewResId, this);

            int noNetworkViewResId = typedArray.getResourceId(R.styleable.CommonLoadStateLayout_loading, R.layout.common_no_network_view);
            mNoNetworkView = LayoutInflater.from(mContext).inflate(noNetworkViewResId, this);
        }
        typedArray.recycle();
    }

}

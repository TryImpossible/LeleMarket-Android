package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bynn.common.R;
import com.bynn.common.utils.ToastUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadStateLayout extends FrameLayout {
    /**
     * 加载中
     */
    public static final int                   LOADING    = 1;
    /**
     * 加载成功
     */
    public static final int                   SUCCESS    = 2;
    /**
     * 加载失败，重试
     */
    public static final int                   FAILURE    = 3;
    /**
     * 数据为空
     */
    public static final int                   EMPTY      = 4;
    /**
     * 无网络
     */
    public static final int                   NO_NETWORK = 5;
    /**
     * 上下文
     */
    private             Context               mContext;
    /**
     * 加载视图构造器
     */
    private             LoadingView.Builder   mLoadingViewBuilder;
    /**
     * 错误视图构造器
     */
    private             FailureView.Builder   mFailureViewBuilder;
    /**
     * 空视图构造器
     */
    private             EmptyView.Builder     mEmptyViewBuilder;
    /**
     * 无网络视图构造器
     */
    private             NoNetworkView.Builder mNoNetworkViewBuilder;
    /**
     * 上次加载状态，默认成功
     */
    private             int                   mLastState = SUCCESS;

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
    }

    private void initLoadingViewBuilder() {
        mLoadingViewBuilder = new LoadingView.Builder(mContext);
        addView(mLoadingViewBuilder.create());
    }

    private void initFailureViewBuilder() {
        mFailureViewBuilder = new FailureView.Builder(mContext);
        addView(mFailureViewBuilder.create());
    }

    private void initEmptyViewBuilder() {
        mEmptyViewBuilder = new EmptyView.Builder(mContext);
        addView(mEmptyViewBuilder.create());
    }

    private void initNoNetworkViewBuilder() {
        mNoNetworkViewBuilder = new NoNetworkView.Builder(mContext);
        addView(mNoNetworkViewBuilder.create());
    }

    private void hideLastView() {
        switch (mLastState) {
            case LOADING:
                mLoadingViewBuilder.setVisible(false);
                break;
            case FAILURE:
                mFailureViewBuilder.setVisible(false);
                break;
            case EMPTY:
                mEmptyViewBuilder.setVisible(false);
                break;
            case NO_NETWORK:
                mNoNetworkViewBuilder.setVisible(false);
                break;
            default:
                break;
        }
    }

    /**
     * 显示加载视图
     */
    public void showLoading() {
        if (mLastState == LOADING) {
            return;
        }
        hideLastView();

        if (mLoadingViewBuilder == null) {
            initLoadingViewBuilder();
        } else {
            mLoadingViewBuilder.setVisible(true);
        }
        mLastState = LOADING;
    }

    /**
     * 显示失败视图
     */
    public void showFailure() {
        if (mLastState == FAILURE) {
            return;
        }
        hideLastView();

        if (mFailureViewBuilder == null) {
            initFailureViewBuilder();
        } else {
            mFailureViewBuilder.setVisible(true);
        }
        mLastState = FAILURE;
    }

    /**
     * 显示空视图
     */
    public void showEmpty() {
        if (mLastState == EMPTY) {
            return;
        }
        hideLastView();

        if (mEmptyViewBuilder == null) {
            initEmptyViewBuilder();
        } else {
            mEmptyViewBuilder.setVisible(true);
        }
        mLastState = EMPTY;
    }

    /**
     * 显示无网络视图
     */
    public void showNoNetwork() {
        if (mLastState == NO_NETWORK) {
            return;
        }
        hideLastView();

        if (mNoNetworkViewBuilder == null) {
            initNoNetworkViewBuilder();
        } else {
            mNoNetworkViewBuilder.setVisible(true);
        }
        mLastState = NO_NETWORK;
    }

    public LoadingView.Builder getLoadingViewBuilder() {
        if (mLoadingViewBuilder == null) {
            initLoadingViewBuilder();
        }
        return mLoadingViewBuilder;
    }

    public FailureView.Builder getFailureViewBuilder() {
        if (mFailureViewBuilder == null) {
            initFailureViewBuilder();
        }
        return mFailureViewBuilder;
    }

    public EmptyView.Builder getEmptyViewBuilder() {
        if (mEmptyViewBuilder == null) {
            initEmptyViewBuilder();
        }
        return mEmptyViewBuilder;
    }

    public NoNetworkView.Builder getNoNetworkViewBuilder() {
        if (mNoNetworkViewBuilder == null) {
            initNoNetworkViewBuilder();
        }
        return mNoNetworkViewBuilder;
    }
}

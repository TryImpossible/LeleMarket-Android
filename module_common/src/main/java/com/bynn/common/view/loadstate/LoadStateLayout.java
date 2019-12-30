package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadStateLayout extends FrameLayout {
    /**
     * 加载中
     */
    public static final int LOADING    = 1;
    /**
     * 加载成功
     */
    public static final int SUCCESS    = 2;
    /**
     * 加载失败，重试
     */
    public static final int FAILURE    = 3;
    /**
     * 数据为空
     */
    public static final int EMPTY      = 4;
    /**
     * 无网络
     */
    public static final int NO_NETWORK = 5;

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
    }

}

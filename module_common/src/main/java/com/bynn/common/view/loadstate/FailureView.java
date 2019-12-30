package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class FailureView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;

    public FailureView(Context context) {
        this(context, null);
    }

    public FailureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FailureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FailureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
    }
}

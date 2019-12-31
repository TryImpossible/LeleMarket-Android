package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bynn.common.R;

public class LoadingView extends LinearLayout {
    /**
     * 上下文
     */
    private Context  mContext;
    /**
     * 文字
     */
    private TextView mTvPrompt;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_loading_view, this);
        mTvPrompt = view.findViewById(R.id.tv_prompt);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CommonLoadingView, defStyleAttr, 0);
        if (null != typedArray) {
            String content = typedArray.getString(R.styleable.CommonLoadingView_loading_prompt);
            if (!TextUtils.isEmpty(content)) {
                setPrompt(content);
            }
        }
        typedArray.recycle();
    }

    /**
     * 设置文字
     *
     * @param resId
     */
    private void setPrompt(@StringRes int resId) {
        mTvPrompt.setText(resId);
    }

    /**
     * 设置文字
     *
     * @param content
     */
    private void setPrompt(String content) {
        mTvPrompt.setText(content);
    }
}

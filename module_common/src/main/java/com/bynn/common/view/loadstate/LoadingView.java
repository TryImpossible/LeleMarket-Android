package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bynn.common.R;

public class LoadingView extends LinearLayout {
    /**
     * 上下文
     */
    private Context           mContext;
    /**
     * 刷新图标
     */
    private ImageView         mIvRefresh;
    /**
     * 刷新图标背景（Drawable）
     */
    private AnimationDrawable mAnimationDrawable;
    /**
     * 文字
     */
    private TextView          mTvPrompt;

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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimationDrawable != null) {
            mAnimationDrawable.setCallback(null);
            mAnimationDrawable = null;
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_loading_view, this);
        mIvRefresh = view.findViewById(R.id.iv_refresh_loading);
        mAnimationDrawable = (AnimationDrawable) mIvRefresh.getBackground();
        mAnimationDrawable.start();

        mTvPrompt = view.findViewById(R.id.tv_prompt);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0);
        if (null != typedArray) {
            String prompt = typedArray.getString(R.styleable.LoadingView_prompt);
            if (!TextUtils.isEmpty(prompt)) {
                setPrompt(prompt);
            }
        }
        typedArray.recycle();
    }

    /**
     * 设置文字
     *
     * @param resId
     */
    public void setPrompt(@StringRes int resId) {
        mTvPrompt.setText(resId);
    }

    /**
     * 设置文字
     *
     * @param prompt
     */
    public void setPrompt(String prompt) {
        mTvPrompt.setText(prompt);
    }

    /**
     * 设置LoadingView是否可见
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        if (visible) {
            mAnimationDrawable.start();
        } else {
            mAnimationDrawable.stop();
        }
        setVisibility(visible ? VISIBLE : GONE);
    }

    public static class Builder {
        private LoadingView instance;

        public Builder(Context context) {
            this.instance = new LoadingView(context);
        }

        /**
         * 设置文字
         *
         * @param resId
         */
        public Builder setPrompt(@StringRes int resId) {
            instance.setPrompt(resId);
            return this;
        }

        /**
         * 设置文字
         *
         * @param prompt
         */
        public Builder setPrompt(String prompt) {
            instance.setPrompt(prompt);
            return this;
        }

        /**
         * 设置LoadingView是否可见
         *
         * @param visible
         */
        public Builder setVisible(boolean visible) {
            instance.setVisible(visible);
            return this;
        }

        public LoadingView create() {
            return instance;
        }
    }
}

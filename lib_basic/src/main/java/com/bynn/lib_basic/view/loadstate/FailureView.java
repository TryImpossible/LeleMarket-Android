package com.bynn.lib_basic.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bynn.lib_basic.R;

public class FailureView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 父容器
     */
    private LinearLayout mLlContainer;
    /**
     * 图标
     */
    private ImageView mIvIcon;
    /**
     * 文字
     */
    private TextView mTvPrompt;
    /**
     * 重试
     */
    private OnClickListener mReloadClickListener;

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.basic_failure_view, this);
        mLlContainer = view.findViewById(R.id.container);
        mLlContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReloadClickListener != null) {
                    mReloadClickListener.onClick(v);
                }
            }
        });
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvPrompt = view.findViewById(R.id.tv_prompt);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.FailureView, defStyleAttr, 0);
        if (null != typedArray) {
            int iconResId = typedArray.getResourceId(R.styleable.FailureView_icon, 0);
            if (iconResId != 0) {
                setIcon(iconResId);
            }
            String prompt = typedArray.getString(R.styleable.FailureView_prompt);
            if (!TextUtils.isEmpty(prompt)) {
                setPrompt(prompt);
            }
        }
        typedArray.recycle();
    }

    /**
     * 设置图标
     *
     * @param resId
     */
    public void setIcon(@DrawableRes int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 设置图标
     *
     * @param drawable
     */
    public void setIcon(Drawable drawable) {
        mIvIcon.setImageDrawable(drawable);
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
     * 设置按钮事件
     *
     * @param listener
     */
    public void setOnReloadClickListener(OnClickListener listener) {
        mReloadClickListener = listener;
    }

    /**
     * 设置FailureView是否可见
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        setVisibility(visible ? VISIBLE : GONE);
    }

    public static class Builder {
        private FailureView instance;

        public Builder(Context context) {
            this.instance = new FailureView(context);
        }

        /**
         * 设置图标
         *
         * @param resId
         */
        public Builder setIcon(@DrawableRes int resId) {
            instance.setIcon(resId);
            return this;
        }

        /**
         * 设置图标
         *
         * @param drawable
         */
        public Builder setIcon(Drawable drawable) {
            instance.setIcon(drawable);
            return this;
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
         * 设置按钮事件
         *
         * @param listener
         */
        public Builder setOnReloadClickListener(OnClickListener listener) {
            instance.setOnReloadClickListener(listener);
            return this;
        }

        /**
         * 设置FailureView是否可见
         *
         * @param visible
         */
        public Builder setVisible(boolean visible) {
            instance.setVisible(visible);
            return this;
        }

        public FailureView create() {
            return instance;
        }
    }

}

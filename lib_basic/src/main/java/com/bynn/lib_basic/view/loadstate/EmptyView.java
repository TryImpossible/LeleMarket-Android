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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bynn.lib_basic.R;

public class EmptyView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图标
     */
    private ImageView mIvIcon;
    /**
     * 文字
     */
    private TextView mTvPrompt;
    /**
     * 按钮
     */
    private Button mBtnGo;
    /**
     * 按钮事件
     */
    private OnClickListener mButtonClickListener;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.basic_empty_view, this);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvPrompt = view.findViewById(R.id.tv_prompt);
        mBtnGo = view.findViewById(R.id.btn_go);
        mBtnGo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mButtonClickListener) {
                    mButtonClickListener.onClick(v);
                }
            }
        });

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, 0);
        if (null != typedArray) {
            int iconResId = typedArray.getResourceId(R.styleable.EmptyView_icon, 0);
            if (iconResId != 0) {
                setIcon(iconResId);
            }
            String prompt = typedArray.getString(R.styleable.EmptyView_prompt);
            if (!TextUtils.isEmpty(prompt)) {
                setPrompt(prompt);
            }
            String goText = typedArray.getString(R.styleable.EmptyView_go_text);
            if (!TextUtils.isEmpty(goText)) {
                setButtonText(goText);
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
     * 设置按钮是否可见
     *
     * @param visible
     */
    public void setButtonVisible(boolean visible) {
        mBtnGo.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 设置按钮文字
     *
     * @param resId
     */
    public void setButtonText(@StringRes int resId) {
        mBtnGo.setText(resId);
    }

    /**
     * 设置按钮文字
     *
     * @param text
     */
    public void setButtonText(String text) {
        mBtnGo.setText(text);
    }

    /**
     * 设置按钮事件
     *
     * @param listener
     */
    public void setOnButtonClickListener(OnClickListener listener) {
        mButtonClickListener = listener;
    }

    /**
     * 设置EmptyView是否可见
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        setVisibility(visible ? VISIBLE : GONE);
    }

    public static class Builder {
        private EmptyView instance;

        public Builder(Context context) {
            this.instance = new EmptyView(context);
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
         * 设置按钮是否可见
         *
         * @param visible
         */
        public Builder setButtonVisible(boolean visible) {
            instance.setButtonVisible(visible);
            return this;
        }

        /**
         * 设置按钮文字
         *
         * @param resId
         */
        public Builder setButtonText(@StringRes int resId) {
            instance.setButtonText(resId);
            return this;
        }

        /**
         * 设置按钮文字
         *
         * @param text
         */
        public Builder setButtonText(String text) {
            instance.setButtonText(text);
            return this;
        }

        /**
         * 设置按钮事件
         *
         * @param listener
         */
        public Builder setOnButtonClickListener(OnClickListener listener) {
            instance.setOnButtonClickListener(listener);
            return this;
        }

        /**
         * 设置是否可见
         *
         * @param visible
         * @return
         */
        public Builder setVisible(boolean visible) {
            instance.setVisible(visible);
            return this;
        }

        public EmptyView create() {
            return instance;
        }

    }
}

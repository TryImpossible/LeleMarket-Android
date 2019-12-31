package com.bynn.common.view.loadstate;

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

import com.bynn.common.R;

public class FailureView extends LinearLayout {
    /**
     * 上下文
     */
    private Context              mContext;
    /**
     * 父容器
     */
    private LinearLayout         mLlContainer;
    /**
     * 图标
     */
    private ImageView            mIvIcon;
    /**
     * 文字
     */
    private TextView             mTvPrompt;
    /**
     * 重试
     */
    private View.OnClickListener mReloadClickListener;

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.common_failure_view, this);
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

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CommonFailureView, defStyleAttr, 0);
        if (null != typedArray) {
            int iconResId = typedArray.getResourceId(R.styleable.CommonFailureView_failure_icon, 0);
            if (iconResId != 0) {
                setIcon(iconResId);
            }
            String content = typedArray.getString(R.styleable.CommonFailureView_failure_prompt);
            if (!TextUtils.isEmpty(content)) {
                setPrompt(content);
            }
        }
        typedArray.recycle();
    }

    /**
     * 设置图标
     *
     * @param resId
     */
    private void setIcon(@DrawableRes int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 设置图标
     *
     * @param drawable
     */
    private void setIcon(Drawable drawable) {
        mIvIcon.setImageDrawable(drawable);
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

    /**
     * 设置按钮事件
     *
     * @param listener
     */
    private void setOnButtonClickListener(View.OnClickListener listener) {
        mReloadClickListener = listener;
    }
}

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bynn.common.R;

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
    private View.OnClickListener mButtonClickListener;

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.common_empty_view, this);
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

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CommonEmptyView, defStyleAttr, 0);
        if (null != typedArray) {
            int iconResId = typedArray.getResourceId(R.styleable.CommonEmptyView_icon, 0);
            if (iconResId != 0) {
                setIcon(iconResId);
            }
            String content = typedArray.getString(R.styleable.CommonEmptyView_prompt);
            if (!TextUtils.isEmpty(content)) {
                setPrompt(content);
            }
            String buttonText = typedArray.getString(R.styleable.CommonEmptyView_button_title);
            if (!TextUtils.isEmpty(buttonText)) {
                setPrompt(buttonText);
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
     * 设置按钮文字
     *
     * @param resId
     */
    private void setButtonTitle(@StringRes int resId) {
        mBtnGo.setText(resId);
    }

    /**
     * 设置按钮文字
     *
     * @param text
     */
    private void setButtonTitle(String text) {
        mBtnGo.setText(text);
    }

    /**
     * 设置按钮事件
     *
     * @param listener
     */
    private void setOnButtonClickListener(View.OnClickListener listener) {
        mButtonClickListener = listener;
    }
}

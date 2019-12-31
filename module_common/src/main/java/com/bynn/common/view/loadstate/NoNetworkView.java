package com.bynn.common.view.loadstate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
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

public class NoNetworkView extends LinearLayout {
    /**
     * 上下文
     */
    private Context              mContext;
    /**
     * 图标
     */
    private ImageView            mIvIcon;
    /**
     * 文字
     */
    private TextView             mTvPrompt;
    /**
     * 设定按钮
     */
    private Button               mBtnSetting;
    /**
     * 重试
     */
    private View.OnClickListener mSettingClickListener;

    public NoNetworkView(Context context) {
        this(context, null);
    }

    public NoNetworkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoNetworkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoNetworkView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_no_network_view, this);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvPrompt = view.findViewById(R.id.tv_prompt);
        mBtnSetting = view.findViewById(R.id.btn_setting);
        mBtnSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                if (mSettingClickListener != null) {
                    mSettingClickListener.onClick(v);
                }
            }
        });

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CommonNoNetworkView, defStyleAttr, 0);
        if (null != typedArray) {
            int iconResId = typedArray.getResourceId(R.styleable.CommonNoNetworkView_no_network_icon, 0);
            if (iconResId != 0) {
                setIcon(iconResId);
            }
            String content = typedArray.getString(R.styleable.CommonNoNetworkView_no_network_prompt);
            if (!TextUtils.isEmpty(content)) {
                setPrompt(content);
            }
            String settingText = typedArray.getString(R.styleable.CommonNoNetworkView_no_network_setting_text);
            if (!TextUtils.isEmpty(settingText)) {
                setSettingTitle(settingText);
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
     * 设置设定标题
     *
     * @param resId
     */
    private void setSettingTitle(@StringRes int resId) {
        mBtnSetting.setText(resId);
    }

    /**
     * 设置设定标题
     *
     * @param text
     */
    private void setSettingTitle(String text) {
        mBtnSetting.setText(text);
    }


    /**
     * 设置按钮事件
     *
     * @param listener
     */
    private void setOnButtonClickListener(View.OnClickListener listener) {
        mSettingClickListener = listener;
    }
}

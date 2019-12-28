package com.bynn.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.bynn.common.R;
import com.bynn.common.utils.ToastUtils;
import com.google.gson.internal.bind.ObjectTypeAdapter;

public class HeaderView extends RelativeLayout implements View.OnClickListener {

    /**
     * 上下文
     */
    private Context              mContext;
    /**
     * 左侧
     */
    private LinearLayout         mLlLeft;
    /**
     * 返回图标
     */
    private ImageView            mIvBackIcon;
    /**
     * 返回文案
     */
    private TextView             mTvBackText;
    /**
     * 中间
     */
    private FrameLayout          mFlMiddle;
    /**
     * 标题
     */
    private TextView             mTvTitleText;
    /**
     * 右侧
     */
    private FrameLayout          mFlRight;
    /**
     * 菜单图标
     */
    private ImageView            mIvMenuIcon;
    /**
     * 菜单文案
     */
    private TextView             mTvMenuText;
    /**
     * 底部分隔线
     */
    private View                 mVLine;
    /**
     * 返回事件
     */
    private View.OnClickListener mBackClickListener;
    /**
     * 菜单事件
     */
    private View.OnClickListener mMenuClickListener;

    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_left) {
            if (null != mBackClickListener) {
                mBackClickListener.onClick(v);
            }
        }
        if (v.getId() == R.id.fl_right) {
            if (null != mMenuClickListener) {
                mMenuClickListener.onClick(v);
            }
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.common_header_view, this);
        mLlLeft = view.findViewById(R.id.ll_left);
        mLlLeft.setOnClickListener(this);
        mLlLeft = view.findViewById(R.id.ll_left);
        mIvBackIcon = view.findViewById(R.id.iv_back);
        mTvBackText = view.findViewById(R.id.tv_back);
        mFlMiddle = view.findViewById(R.id.fl_middle);
        mTvTitleText = view.findViewById(R.id.tv_title);
        mFlRight = view.findViewById(R.id.fl_right);
        mFlRight.setOnClickListener(this);
        mIvMenuIcon = view.findViewById(R.id.iv_menu);
        mTvMenuText = view.findViewById(R.id.tv_menu);
        mVLine = view.findViewById(R.id.v_line);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CommonHeaderView, defStyleAttr, 0);
        if (null != typedArray) {
            boolean backIconVisible = typedArray.getBoolean(R.styleable.CommonHeaderView_back_icon_visible, true);
            setBackIconVisible(backIconVisible);

            int backIconResId = typedArray.getResourceId(R.styleable.CommonHeaderView_back_icon, 0);
            if (backIconResId != 0) {
                setBackIcon(backIconResId);
            }
            String backText = typedArray.getString(R.styleable.CommonHeaderView_back_text);
            setBackText(backText);


            String title = typedArray.getString(R.styleable.CommonHeaderView_title);
            setTitleText(title);

            int menuIconResId = typedArray.getResourceId(R.styleable.CommonHeaderView_menu_icon, 0);
            if (menuIconResId != 0) {
                setMenuIcon(menuIconResId);
            }
            String menuText = typedArray.getString(R.styleable.CommonHeaderView_menu_text);
            if (!TextUtils.isEmpty(menuText)) {
                setMenuText(menuText);
            }

            boolean lineVisible = typedArray.getBoolean(R.styleable.CommonHeaderView_line_visible, true);
            setLineVisible(lineVisible);

            typedArray.recycle();
        }
    }

    /**
     * 设置返回图标
     *
     * @param resId
     */
    public void setBackIcon(@DrawableRes int resId) {
        mIvBackIcon.setImageResource(resId);
    }

    /**
     * 设置返回图标
     *
     * @param drawable
     */
    public void setBackIcon(Drawable drawable) {
        mIvBackIcon.setImageDrawable(drawable);
    }

    /**
     * 设置返回图标是否可见
     *
     * @param visible
     */
    public void setBackIconVisible(boolean visible) {
        mIvBackIcon.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 设置返回文案
     *
     * @param resId
     */
    public void setBackText(@StringRes int resId) {
        mTvBackText.setText(resId);
    }

    /**
     * 设置返回文案
     *
     * @param text
     */
    public void setBackText(String text) {
        mTvBackText.setText(text);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitleText(@StringRes int resId) {
        mTvTitleText.setText(resId);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitleText(String title) {
        mTvTitleText.setText(title);
    }

    /**
     * 设置菜单图标
     *
     * @param resId
     */
    public void setMenuIcon(@DrawableRes int resId) {
        mIvMenuIcon.setImageResource(resId);
    }

    /**
     * 设置菜单图标
     *
     * @param drawable
     */
    public void setMenuIcon(Drawable drawable) {
        mIvMenuIcon.setImageDrawable(drawable);
    }

    /**
     * 设置菜单文案
     *
     * @param resId
     */
    public void setMenuText(@StringRes int resId) {
        mTvMenuText.setText(resId);
    }

    /**
     * 设置菜单文案
     *
     * @param text
     */
    public void setMenuText(String text) {
        mTvMenuText.setText(text);
    }

    /**
     * 设置分隔线是否可见
     *
     * @param visible
     */
    public void setLineVisible(boolean visible) {
        mVLine.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 添加返回事件
     *
     * @param listener
     */
    public void setOnBackClickListener(View.OnClickListener listener) {
        this.mBackClickListener = listener;
    }

    /**
     * 添加菜单事件
     *
     * @param listener
     */
    public void setOnMenuClickListener(View.OnClickListener listener) {
        this.mMenuClickListener = listener;
    }
}

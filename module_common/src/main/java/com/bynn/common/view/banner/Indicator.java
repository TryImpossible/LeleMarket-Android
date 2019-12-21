package com.bynn.common.view.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bynn.common.R;
import com.bynn.common.qmui.QMUIDisplayHelper;

public class Indicator extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 是否显示
     */
    private boolean mVisible;
    /**
     * 单个指示器大小，单位px
     */
    private int     mItemSize;
    /**
     * 指示器默认颜色
     */
    private int     mColor;
    /**
     * 指示器选中颜色
     */
    private int     mCheckedColor;
    /**
     * 单个指示器边距
     */
    private int     mItemMargin;
    /**
     * 指示器数量
     */
    private int     mCount;
    /**
     * 上次选中项
     */
    private int     mLastCheckedItem;

    public Indicator(Context context) {
        this(context, null);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Indicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        setOrientation(HORIZONTAL);

        // 默认显示
        mVisible = true;
        // 默认6dp
        mItemSize = QMUIDisplayHelper.dp2px(mContext, 6);
        mColor = ContextCompat.getColor(mContext, R.color.common_white);
        mCheckedColor = ContextCompat.getColor(mContext, R.color.common_colorAccent);
        // 默认8dp
        mItemMargin = QMUIDisplayHelper.dp2px(mContext, 8);
        // 默认空
        mCount = 0;
        // 默认选中第一项
        mLastCheckedItem = 0;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonIndicator, defStyleAttr, 0);
        if (null != typedArray) {
            mVisible = typedArray.getBoolean(R.styleable.CommonIndicator_visible, mVisible);
            mItemSize = typedArray.getDimensionPixelSize(R.styleable.CommonIndicator_item_size, mItemSize);
            mColor = typedArray.getColor(R.styleable.CommonIndicator_color, mColor);
            mCheckedColor = typedArray.getColor(R.styleable.CommonIndicator_checked_color, mCheckedColor);
            mItemMargin = typedArray.getDimensionPixelSize(R.styleable.CommonIndicator_item_margin, mItemMargin);
            mCount = typedArray.getInteger(R.styleable.CommonIndicator_count, mCount);
        }

    }

    /**
     * 设置选中的项
     *
     * @param position 索引
     */
    public void setCheckedItem(int position) {
        ((Dot) getChildAt(mLastCheckedItem)).setChecked(false);
        ((Dot) getChildAt(position)).setChecked(true);
        mLastCheckedItem = position;
    }

    /**
     * 是否显示
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        mVisible = visible;
        if (mVisible) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    /**
     * 设置单个尺寸
     *
     * @param size
     */
    public void setItemSize(int size) {
        mItemSize = size;
    }

    /**
     * 设置默认颜色
     *
     * @param color
     */
    public void setColor(int color) {
        mColor = color;
        for (int i = 0; i < mCount; i++) {
            Dot dot = (Dot) getChildAt(i);
            dot.setColor(mColor);
        }
    }

    /**
     * 设置选中颜色
     *
     * @param checkedColor
     */
    public void setCheckedColor(int checkedColor) {
        mCheckedColor = checkedColor;
        for (int i = 0; i < mCount; i++) {
            Dot dot = (Dot) getChildAt(i);
            dot.setCheckedColor(mCheckedColor);
        }
    }

    /**
     * 设置单个相邻边距
     *
     * @param margin
     */
    public void setItemMargin(int margin) {
        mItemMargin = margin;
    }

    /**
     * 显示Indicator
     *
     * @param count 数量
     */
    public void show(int count) {
        mCount = count;
        removeAllViews();
        for (int i = 0; i < mCount; i++) {
            Dot dot = new Dot(mContext);
            dot.setColor(mColor);
            dot.setCheckedColor(mCheckedColor);
            dot.setChecked(i == mLastCheckedItem);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemSize, mItemSize);
            layoutParams.setMargins(mItemMargin, 0, 0, 0);
            dot.setLayoutParams(layoutParams);
            addView(dot);
        }
    }

    /**
     * 单个圆点
     */
    private class Dot extends View {

        /**
         * 画笔
         */
        private Paint mPaint;
        /**
         * 默认颜色
         */
        private int   mColor;
        /**
         * 选中颜色
         */
        private int   mCheckedColor;

        public Dot(Context context) {
            super(context);
            init(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 2;
            canvas.drawCircle(width / 2, height / 2, radius, mPaint);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            setChecked(enabled);
        }

        private void init(Context context) {
            mContext = context;

            // 默认白色
            mColor = ContextCompat.getColor(mContext, R.color.common_white);
            // 默认红色
            mCheckedColor = ContextCompat.getColor(mContext, R.color.common_colorAccent);

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
        }

        /**
         * 设置默认颜色
         *
         * @param color
         */
        public void setColor(int color) {
            mColor = color;
            invalidate();
        }

        /**
         * 设置选中颜色
         *
         * @param color
         */
        public void setCheckedColor(int color) {
            mCheckedColor = color;
            invalidate();
        }

        /**
         * 切换选中状态
         *
         * @param checked
         */
        public void setChecked(boolean checked) {
            mPaint.setColor(checked ? mCheckedColor : mColor);
            invalidate();
        }
    }

}

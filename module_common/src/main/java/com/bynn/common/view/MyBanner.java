package com.bynn.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bynn.common.R;
import com.bynn.common.qmui.QMUIDisplayHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyBanner extends LinearLayout {

    /**
     * 上下文
     */
    private Context      mContext;
    /**
     * ViewPager,实现图片切换
     */
    private ViewPager    mViewPager;
    /**
     * 小圆点容器
     */
    private LinearLayout mLldots;

    /**
     * PagerAdapter
     */
    private PagerAdapter mPagerAdapter;
    /**
     * 图片集合
     */
    private List<String> mImageList;
    /**
     * 上次选中的位置
     */
    private int          mLastPosition;
    /**
     * rxjava避免内存泄露，这里直接理解为清除定时器
     */
    private Disposable   mDisposable;
    /**
     * 是否自动播放
     */
    private boolean      mIsAutoPlay;
    /**
     * 是否循环播放
     */
    private boolean      mIsLoop;
    /**
     * 圆点大小，单位px
     */
    private int          mDotSize;
    /**
     * 圆点默认颜色
     */
    private int          mDotColor;
    /**
     * 圆点选中颜色
     */
    private int          mDotCheckedColor;
    /**
     * 圆点边距
     */
    private int          mDotMargin;

    public MyBanner(Context context) {
        super(context);
        init(context, null);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mLastPosition = 0;
        mIsAutoPlay = true;
        mIsLoop = true;
        mDotSize = QMUIDisplayHelper.dp2px(mContext, 6);
        mDotColor = ContextCompat.getColor(mContext, R.color.common_white);
        mDotCheckedColor = ContextCompat.getColor(mContext, R.color.common_colorAccent);
        mDotMargin = QMUIDisplayHelper.dp2px(mContext, 8);

        View view = LayoutInflater.from(context).inflate(R.layout.common_my_banner, this);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mLldots = (LinearLayout) view.findViewById(R.id.ll_dots);
    }

    public void setImageList(@NotNull List<String> imageList) {
        mImageList = imageList;
        int length = imageList.size();
        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                // 初始化时，往容器中添加图片

                position = position % length; // 计算实际的位置
                ImageView imageView = new ImageView(mContext);
                Glide.with(mContext)
                        .load(mImageList.get(position))
                        .into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                // 销毁时，操作容器移除图片
                container.removeView((View) object);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        // 一次加载2张，加上本身ViewPgaer的1张，总共3张
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % length;
                if (null != mLldots) {
                    ((Dot) mLldots.getChildAt(mLastPosition)).setChecked(false);
                    ((Dot) mLldots.getChildAt(position)).setChecked(true);
                }
                mLastPosition = position;
                if (!mIsLoop) {
                    stopPlay();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // SCROLL_STATE_IDLE ：空闲状态
                // SCROLL_STATE_DRAGGING ：滑动状态
                // SCROLL_STATE_SETTLING ：滑动后滑翔的状态
            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        stopPlay();
                        break;
                    case MotionEvent.ACTION_UP:
                        startPlay();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        for (int i = 0; i < length; i++) {
            Dot dot = new Dot(mContext);
            dot.setColor(mDotColor);
            dot.setCheckedColor(mDotCheckedColor);
            dot.setChecked(i == mLastPosition);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            layoutParams.setMargins(mDotMargin, 0, 0, 0);
            mLldots.addView(dot, layoutParams);
        }
    }

    /**
     * 设置当前选中
     *
     * @param item 位置
     */
    public void setCurrentItem(int item) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(item);
        }
    }

    /**
     * 设置当前选中
     *
     * @param item         位置
     * @param smoothScroll 是否平滑滚动
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(item, smoothScroll);
        }
    }

    /**
     * 是否自动播放
     *
     * @return
     */
    public boolean isAutoPlay() {
        return mIsAutoPlay;
    }

    /**
     * 是否循环播放
     *
     * @return
     */
    public boolean isLoop() {
        return mIsLoop;
    }

    /**
     * 设置是否循环播放
     *
     * @param isLoop
     */
    public void setLoop(boolean isLoop) {
        mIsLoop = isLoop;
    }

    /**
     * 设置是否自动播放
     *
     * @param isAutoPlay
     */
    public void setAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
    }

    /**
     * 开始播放
     */
    public void startPlay() {
        Observable.interval(3, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        if (null != mDisposable && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    /**
     * 设置圆点大小
     *
     * @param dotSize
     */
    public void setDotSize(int dotSize) {
        mDotSize = dotSize;
    }

    /**
     * 设置圆点默认颜色
     *
     * @param dotColor
     */
    public void setDotColor(int dotColor) {
        mDotColor = dotColor;
    }

    /**
     * 设置圆点选中颜色
     *
     * @param dotCheckedColor
     */
    public void setDotCheckedColor(int dotCheckedColor) {
        mDotCheckedColor = dotCheckedColor;
    }

    /**
     * 设置圆点相邻边距
     *
     * @param dotMargin
     */
    public void setDotMargin(int dotMargin) {
        mDotMargin = dotMargin;
    }

    private class Dot extends View {

        /**
         * 上下文
         */
        private Context mContext;
        /**
         * 画笔
         */
        private Paint   mPaint;
        /**
         * 默认颜色
         */
        private int     mColor;
        /**
         * 选中颜色
         */
        private int     mCheckedColor;

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

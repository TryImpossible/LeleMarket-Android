package com.bynn.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
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
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.bumptech.glide.Glide;
import com.bynn.common.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyBanner extends LinearLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mLldots;

    private PagerAdapter mPagerAdapter;
    private List<String> mImageList;
    private int mLastPosition;
    private Disposable mDisposable;
    private boolean mIsAutoPlay;
    private boolean mIsLoop;

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
                position = position / length;
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
                container.removeView((View) object);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mImageList.size();
                if (null != mLldots) {
                    mLldots.getChildAt(mLastPosition).setEnabled(false);
                    mLldots.getChildAt(position).setEnabled(true);
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
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.common_dot_selector));
            imageView.setPadding(20, 0, 0, 0);
            imageView.setEnabled(i == mLastPosition);
            mLldots.addView(imageView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setCurrentItem(int item) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(item);
        }
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(item, smoothScroll);
        }
    }

    public boolean isAutoPlay() {
        return mIsAutoPlay;
    }

    public boolean isLoop() {
        return mIsLoop;
    }

    public void setLoop(boolean isLoop) {
        mIsLoop = isLoop;
    }

    public void setAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
        startPlay();
    }

    public void startPlay() {
        Observable.interval(1, 3, TimeUnit.SECONDS)
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

    public void stopPlay() {
        if (null != mDisposable && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

}

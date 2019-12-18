package com.bynn.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bynn.common.R;

public class MyBanner extends LinearLayout {

    private Context      mContext;
    private ViewPager    mViewPager;
    private LinearLayout mLldots;

    private PagerAdapter mPagerAdapter;
    private int          length = 5;

    public MyBanner(Context context) {
        super(context);
        mContext = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.common_my_banner, this);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mLldots = (LinearLayout) view.findViewById(R.id.ll_dots);

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
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.mipmap.common_ic_news);
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

        for (int i = 0; i < length; i++) {
            ImageView iv = new ImageView(context);
            iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.common_dot_selector));
            iv.setPadding(20, 0, 0, 0);
            mLldots.addView(iv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLldots.getChildAt(0).setEnabled(true);
        }

    }
}

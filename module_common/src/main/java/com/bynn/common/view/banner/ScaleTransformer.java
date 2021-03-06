package com.bynn.common.view.banner;

import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.8f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            // [-1,1]
        } else if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < 0) {
                float scaleX = 1 + 0.2f * position;
                Log.d("ScaleTransformer", "transformPage: scaleX:" + scaleX);
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else {
                float scaleX = 1 - 0.2f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
        }
    }
}
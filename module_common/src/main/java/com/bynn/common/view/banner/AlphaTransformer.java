package com.bynn.common.view.banner;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class AlphaTransformer implements ViewPager.PageTransformer {
    private float MINALPHA = 0.5f;

    /**
     * position取值特点：
     * 假设页面从0～1，则：
     * 第一个页面position变化为[0,-1]
     * 第二个页面position变化为[1,0]
     *
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        //不透明->半透明
        if (position < -1 || position > 1) {
            page.setAlpha(MINALPHA);
        } else if (!(position < 0)) {//[1,0]
            //半透明->不透明
            page.setAlpha(MINALPHA + (1 - position) * (1 - MINALPHA));
        } else {//[0,-1]
            page.setAlpha(MINALPHA + (1 + position) * (1 - MINALPHA));
        }
    }
}
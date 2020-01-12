package com.bynn.marketll.module_home.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bynn.common.bean.BannerBean;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.common.view.banner.BannerView;
import com.bynn.common.view.banner.ScaleTransformer;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.bean.ChoicenessBean;
import com.bynn.marketll.module_home.bean.CustomizationBean;
import com.bynn.marketll.module_home.bean.HandpickBean;
import com.bynn.marketll.module_home.bean.MidNavBean;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.internal.FlowLayout;

import java.util.List;

public class ChoicenessAdapter extends BaseSectionMultiItemQuickAdapter<ChoicenessBean, BaseViewHolder> {

    public ChoicenessAdapter(List<ChoicenessBean> data) {
        super(R.layout.home_section_choiceness, data);
        addItemType(ChoicenessBean.BANNER, R.layout.home_item_choiceness_banner);
        addItemType(ChoicenessBean.MIDNVA, R.layout.home_item_choiceness_midnav);
        addItemType(ChoicenessBean.HANDPICK, R.layout.home_item_choiceness_handpick);
        addItemType(ChoicenessBean.CUSTOMIZATION, R.layout.home_item_choiceness_customization);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ChoicenessBean item) {
        helper.setText(R.id.tv_title, item.header);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ChoicenessBean item) {
        switch (helper.getItemViewType()) {
            case ChoicenessBean.BANNER:
                List<BannerBean> banners = (List<BannerBean>) item.getItem();
                BannerView banner = helper.getView(R.id.banner);
                banner.getViewPgaer().setPageMargin(QMUIDisplayHelper.dp2px(mContext, 16));
                banner.getIndicator().setColor(Color.GRAY);
                banner.getIndicator().setCheckedColor(ContextCompat.getColor(mContext, R.color.basic_white));
                banner.setOnItemClickListener(new BannerView.OnItemClickListener() {
                    @Override
                    public void OnClick(int position) {
                        if (mBannerClickListener != null) {
                            mBannerClickListener.onClick(banners.get(position), position);
                        }
                    }
                });
                banner.setImageList(BannerBean.getBannerImageList(banners));
                banner.startPlay();
                break;
            case ChoicenessBean.MIDNVA:
                FlowLayout flowLayout = helper.getView(R.id.flowLayout);
                flowLayout.removeAllViews();
                for (MidNavBean bean : (List<MidNavBean>) item.getItem()) {
                    ImageView image = new ImageView(mContext);
                    image.setLayoutParams(new LinearLayout.LayoutParams(QMUIDisplayHelper.dp2px(mContext, 44), QMUIDisplayHelper.dp2px(mContext, 44)));
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Glide.with(mContext).load(bean.getImgUrl()).into(image);

                    TextView text = new TextView(mContext);
                    text.setText(bean.getName());
                    text.setTextColor(Color.RED);
                    text.setTextSize(11);
                    text.setTextColor(ContextCompat.getColor(mContext, R.color.basic_text_light));
                    ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = QMUIDisplayHelper.dp2px(mContext, 4);
                    text.setLayoutParams(params);

                    LinearLayout layout = new LinearLayout(mContext);
                    layout.setLayoutParams(new ViewGroup.LayoutParams(QMUIDisplayHelper.getScreenWidth(mContext) / 4, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layout.setBackgroundResource(R.drawable.basic_layout_selector);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setGravity(Gravity.CENTER_HORIZONTAL);
                    layout.addView(image);
                    layout.addView(text);

                    flowLayout.addView(layout);
                }
                break;
            case ChoicenessBean.HANDPICK:
                List<HandpickBean> handpicks = (List<HandpickBean>) item.getItem();
                BannerView handPick = helper.getView(R.id.handpick);
                handPick.getIndicator().setVisible(false);
                // 禁止裁剪子视图
                handPick.getViewPgaer().setClipToPadding(false);
                handPick.getViewPgaer().setPadding(QMUIDisplayHelper.dp2px(mContext, 16), 0, QMUIDisplayHelper.dp2px(mContext, 70), 0);
                handPick.getViewPgaer().setOffscreenPageLimit(3);
                handPick.getViewPgaer().setPageTransformer(false, new ScaleTransformer());
                handPick.setOnItemClickListener(new BannerView.OnItemClickListener() {
                    @Override
                    public void OnClick(int position) {
                        if (mBannerClickListener != null) {
                            mBannerClickListener.onClick(handpicks.get(position), position);
                        }
                    }
                });
                handPick.setImageList(HandpickBean.getHandPickImageList(handpicks), QMUIDisplayHelper.dp2px(mContext, 10));
                break;
            case ChoicenessBean.CUSTOMIZATION:
                CustomizationBean bean = (CustomizationBean) item.getItem();

                ImageView bigIamge = (ImageView) helper.getView(R.id.iv_big_image);
                Glide.with(mContext)
                        .load(bean.getImgUrl())
                        .into(bigIamge);

                ImageView goodsImage1 = (ImageView) helper.getView(R.id.iv_goods_image1);
                Glide.with(mContext)
                        .load(bean.getGoods().get(0).getImgUrl())
                        .into(goodsImage1);
                helper.setText(R.id.tv_goods_name1, bean.getGoods().get(0).getParam1());

                ImageView goodsImage2 = (ImageView) helper.getView(R.id.iv_goods_image2);
                Glide.with(mContext)
                        .load(bean.getGoods().get(1).getImgUrl())
                        .into(goodsImage2);
                helper.setText(R.id.tv_goods_name2, bean.getGoods().get(1).getParam1());


                ImageView goodsImage3 = (ImageView) helper.getView(R.id.iv_goods_image3);
                Glide.with(mContext)
                        .load(bean.getGoods().get(2).getImgUrl())
                        .into(goodsImage3);
                helper.setText(R.id.tv_goods_name3, bean.getGoods().get(2).getParam1());

                break;
            default:
                break;
        }
    }

    private OnBannerClickListener mBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        mBannerClickListener = listener;
    }

    public interface OnBannerClickListener {
        void onClick(Object obj, int position);
    }
}

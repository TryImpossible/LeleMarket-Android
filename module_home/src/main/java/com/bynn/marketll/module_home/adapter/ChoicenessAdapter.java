package com.bynn.marketll.module_home.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bynn.common.view.MyBanner;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.bean.ChoicenessBean;
import com.bynn.marketll.module_home.bean.CustomizationBean;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ChoicenessAdapter extends BaseSectionMultiItemQuickAdapter<ChoicenessBean, BaseViewHolder> {

    public ChoicenessAdapter(List<ChoicenessBean> data) {
        super(R.layout.home_section_choiceness, data);
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
            case ChoicenessBean.HANDPICK:
                MyBanner banner = helper.getView(R.id.banner);
                banner.setAutoPlay(false);
                banner.setDotVisible(false);
                banner.setImageList(item.getItem().getHandPickImageList());
                break;
            case ChoicenessBean.CUSTOMIZATION:
                CustomizationBean bean = item.getItem().getCustomizationBean();

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

}

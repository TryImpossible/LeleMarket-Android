package com.bynn.marketll.module_home.dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bynn.lib_basic.dialog.BaseDialog;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.bean.GoodsPropertyBean;
import com.google.android.material.internal.FlowLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsPropertyDialog extends BaseDialog {

    public static final  String TAG                = "GoodsPropertyDialog";
    private static final String KEY_GOODS_PROPERTY = "GoodsPropertyBean";

    @BindView(R2.id.image)       ImageView    mImage;
    @BindView(R2.id.tv_price)    TextView     mTvPrice;
    @BindView(R2.id.tv_params)   TextView     mTvParams;
    @BindView(R2.id.ll_property) LinearLayout mLlProperty;
    @BindView(R2.id.tv_amount)   TextView     mTvAmount;
    @BindView(R2.id.iv_minus)    ImageView    mIvMinus;
    @BindView(R2.id.iv_plus)     ImageView    mIvPlus;

    private GoodsPropertyBean mGoodsPropertyBean;

    public static GoodsPropertyDialog newInstance(GoodsPropertyBean bean) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_GOODS_PROPERTY, bean);

        GoodsPropertyDialog fragment = new GoodsPropertyDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_dialog_goods_property, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //Dialog外边框透明
        params.dimAmount = 0.5f;
        params.windowAnimations = R.style.BasicBottomDialogAnimation;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.BOTTOM);

        initView();
        return view;
    }

//    @OnClick(R2.id.container)
//    public void onContainerClick() {
//        dismiss();
//    }

    @OnClick(R2.id.iv_close)
    public void onCloseClick() {
        dismiss();
    }

    @OnClick(R2.id.btn_confirm)
    public void onConfirmClick() {
        dismiss();
    }

    @OnClick(R2.id.iv_minus)
    public void onMinusClick() {
        int amount = mGoodsPropertyBean.getAmount();
        if (amount == 1) {
            return;
        }
        amount--;
        mIvMinus.setImageResource(amount > 1 ? R.mipmap.common_ic_press_btn_minus : R.mipmap.common_ic_normal_btn_minus);
        mGoodsPropertyBean.setAmount(amount);
        setAmountText();
        setPriceText();
    }

    @OnClick(R2.id.iv_plus)
    public void onPlusClick() {
        int amount = mGoodsPropertyBean.getAmount();
        amount++;
        mIvMinus.setImageResource(amount > 1 ? R.mipmap.common_ic_press_btn_minus : R.mipmap.common_ic_normal_btn_minus);
        mGoodsPropertyBean.setAmount(amount);
        setAmountText();
        setPriceText();
    }


    public void initView() {
        mGoodsPropertyBean = getArguments().getParcelable(KEY_GOODS_PROPERTY);
        setShowImage();
        setPriceText();
        setParamsText();
        buildPropertyViews();
        setAmountText();
    }

    private void setShowImage() {
        for (int i = 0; i < mGoodsPropertyBean.getTexture().size(); i++) {
            GoodsPropertyBean.TextureBean bean = mGoodsPropertyBean.getTexture().get(i);
            if (bean.getTexture_ids().equals(mGoodsPropertyBean.getDfTexture())) {
                Glide.with(this)
                        .load(bean.getPre_url())
                        .into(mImage);
                break;
            }
        }
    }

    private void setPriceText() {
        SpannableStringBuilder price = new SpanUtils()
                .append("￥")
                .setFontSize(15, true)
                .append(String.format("%.2f", mGoodsPropertyBean.getNow_price() * mGoodsPropertyBean.getAmount()))
                .setFontSize(18, true)
                .setForegroundColor(ContextCompat.getColor(getContext(), R.color.basic_colorAccent))
                .create();
        mTvPrice.setText(price);
    }

    private void setParamsText() {
        StringBuilder str = new StringBuilder();
        str.append("已选: ");
        for (GoodsPropertyBean.ListBeanX listBeanX : mGoodsPropertyBean.getList()) {
            str.append(listBeanX.getTitle() + " - ");
            for (GoodsPropertyBean.ListBeanX.ListBean bean : listBeanX.getList()) {
                if (bean.isSelected()) {
                    str.append(bean.getGname());
                }
            }
            str.append(", ");
        }
        mTvParams.setText(str.toString());
    }

    private void setAmountText() {
        mTvAmount.setText(mGoodsPropertyBean.getAmount() + "");
    }

    private void buildPropertyViews() {
        if (mGoodsPropertyBean.getList() != null && mGoodsPropertyBean.getList().size() > 0) {
            for (int i = 0; i < mGoodsPropertyBean.getList().size(); i++) {
                GoodsPropertyBean.ListBeanX subList = mGoodsPropertyBean.getList().get(i);

                // 属性分类
                String title = subList.getTitle();
                TextView tvTitle = new TextView(getContext());
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                titleParams.topMargin = QMUIDisplayHelper.dp2px(getContext(), 10);
                titleParams.bottomMargin = QMUIDisplayHelper.dp2px(getContext(), 10);
                tvTitle.setLayoutParams(titleParams);
                tvTitle.setText(title);
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.basic_text_normal));
                tvTitle.setTextSize(15);
                mLlProperty.addView(tvTitle, i);

                //属性名称
                List<GoodsPropertyBean.ListBeanX.ListBean> propertyNames = subList.getList();
                List<TextView> textViews = new ArrayList<>();
                if (propertyNames != null && propertyNames.size() > 0) {
                    @SuppressLint("RestrictedApi")
                    FlowLayout flowLayout = new FlowLayout(getContext());
                    for (GoodsPropertyBean.ListBeanX.ListBean nameBean : propertyNames) {
                        String name = nameBean.getGname();
                        TextView tvName = new TextView(getContext());
                        tvName.setTag(nameBean);
                        int margin = QMUIDisplayHelper.dp2px(getContext(), 10);
                        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        nameParams.rightMargin = margin;
                        nameParams.bottomMargin = margin;
                        tvName.setLayoutParams(nameParams);
                        tvName.setText(name);
                        tvName.setTextColor(ContextCompat.getColor(getContext(), nameBean.isSelected() ? R.color.basic_white : R.color.basic_text_normal));
                        tvName.setTextSize(12);
                        tvName.setBackgroundResource(nameBean.isSelected() ? R.drawable.home_btn_property_name_selected : R.drawable.home_btn_property_name_normal);
                        tvName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (TextView tv : textViews) {
                                    GoodsPropertyBean.ListBeanX.ListBean nameBean2 = (GoodsPropertyBean.ListBeanX.ListBean) tv.getTag();
                                    nameBean2.setSelected(nameBean == nameBean2 ? !nameBean.isSelected() : false);
                                    tv.setTextColor(ContextCompat.getColor(getContext(), nameBean2.isSelected() ? R.color.basic_white : R.color.basic_text_normal));
                                    tv.setBackgroundResource(nameBean2.isSelected() ? R.drawable.home_btn_property_name_selected : R.drawable.home_btn_property_name_normal);
                                    setParamsText();
                                }
                                if (!String.valueOf(nameBean.getId()).equals(mGoodsPropertyBean.getDfTexture())) {
                                    mGoodsPropertyBean.setDfTexture(String.valueOf(nameBean.getId()));
                                    setShowImage();
                                }
                            }
                        });
                        flowLayout.addView(tvName);
                        textViews.add(tvName);
                    }
                    mLlProperty.addView(flowLayout, i + 1);
                }

                // 分隔线
                View divider = new View(getContext());
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, QMUIDisplayHelper.dp2px(getContext(), 1) / 2);
                dividerParams.topMargin = QMUIDisplayHelper.dp2px(getContext(), 10);
                divider.setLayoutParams(dividerParams);
                divider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.basic_divide_line));
                mLlProperty.addView(divider, i + 2);
            }
        }
    }

}

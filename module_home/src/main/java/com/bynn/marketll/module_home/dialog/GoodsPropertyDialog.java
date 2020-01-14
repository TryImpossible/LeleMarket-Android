package com.bynn.marketll.module_home.dialog;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bynn.lib_basic.dialog.BaseDialog;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;
import com.bynn.marketll.module_home.bean.GoodsPropertyBean;
import com.google.android.material.internal.FlowLayout;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsPropertyDialog extends BaseDialog {

    public static final  String TAG                = "GoodsPropertyDialog";
    private static final String KEY_GOODS_PROPERTY = "GoodsPropertyBean";

    @BindView(R2.id.image)      ImageView  mImage;
    @BindView(R2.id.tv_price)   TextView   mTvPrice;
    @BindView(R2.id.tv_params)  TextView   mTvParams;
    @BindView(R2.id.flowLayout) FlowLayout mFlowLayout;

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

    @OnClick(R2.id.iv_close)
    public void onCloseClick() {
        dismiss();
    }

    @OnClick(R2.id.btn_confirm)
    public void onConfirmClick() {
        dismiss();
    }

    public void initView() {
        GoodsPropertyBean bean = getArguments().getParcelable(KEY_GOODS_PROPERTY);

        Glide.with(this)
                .load(bean.getPre_url())
                .into(mImage);
        SpannableStringBuilder price = new SpanUtils()
                .append("￥")
                .setFontSize(15, true)
                .append(String.format("%.2f", bean.getNow_price()))
                .setFontSize(18, true)
                .setForegroundColor(ContextCompat.getColor(getContext(), R.color.basic_colorAccent))
                .create();
        mTvPrice.setText(price);


    }

}

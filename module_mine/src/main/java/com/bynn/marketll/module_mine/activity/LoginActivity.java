package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bynn.common.router.MineRoutePath;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.marketll.module_mine.R;
import com.bynn.marketll.module_mine.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MineRoutePath.LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity {

    @BindView(R2.id.iv_default_header)    ImageView mDefaultHeader;
    @BindView(R2.id.et_phone)             EditText  mEtPhone;
    @BindView(R2.id.iv_del)               ImageView mIvDel;
    @BindView(R2.id.et_verification_code) EditText  mEtVerificationCode;
    @BindView(R2.id.tv_send_code)         TextView  mTvSendCode;
    @BindView(R2.id.tv_login)             TextView  mTvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_login);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Glide.with(this)
                .load(R.mipmap.mine_ic_user_default_header)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mDefaultHeader);

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtPhone.getText().toString().trim().length() > 0) {
                    mIvDel.setVisibility(View.VISIBLE);
                } else {
                    mIvDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R2.id.iv_close)
    public void onCloseClick() {
        finish();
    }

    @OnClick(R2.id.iv_wechat)
    public void onWechatClick() {

    }

    @OnClick(R2.id.iv_qq)
    public void onQQClick() {
    }
}

package com.bynn.marketll.module_home.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bynn.lib_basic.dialog.BaseDialog;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareDialog extends BaseDialog {

    public static final String TAG = "ShareDialog";

    public static ShareDialog newInstance() {

        Bundle args = new Bundle();

        ShareDialog fragment = new ShareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_dialog_share, container, false);

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
        window.setBackgroundDrawable(new ColorDrawable());
        window.setGravity(Gravity.BOTTOM);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R2.id.ll_weixin_circle, R2.id.ll_weixin, R2.id.ll_qonze, R2.id.ll_qq,
            R2.id.ll_sina, R2.id.ll_link, R2.id.tv_cancel})
    public void onViewClick(View view) {
        if (view.getId() == R.id.tv_cancel) {
            dismiss();
        } else {
            ShareType type = null;
            if (view.getId() == R.id.ll_weixin_circle) {
                type = ShareType.WEIXIN_CIRCLE;
            } else if (view.getId() == R.id.ll_weixin) {
                type = ShareType.WEIXIN;
            } else if (view.getId() == R.id.ll_qonze) {
                type = ShareType.QZONE;
            } else if (view.getId() == R.id.ll_qq) {
                type = ShareType.QQ;
            } else if (view.getId() == R.id.ll_sina) {
                type = ShareType.SINA;
            } else if (view.getId() == R.id.ll_link) {
                type = ShareType.LINK;
            }
            if (mListener != null) {
                mListener.onClick(type);
            }
            dismiss();
        }
    }

    private OnShareClickListener mListener;

    public void setOnShareClickListener(OnShareClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnShareClickListener {
        void onClick(ShareType type);
    }

    /**
     * 分享类型枚举
     */
    public enum ShareType {
        WEIXIN_CIRCLE,
        WEIXIN,
        QZONE,
        QQ,
        SINA,
        LINK;
    }
}

package com.bynn.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bynn.common.R;


public class ProgressDialog extends Dialog {

    private TextView mTvText;
    private String   text;

    public ProgressDialog(@NonNull Context context, String text) {
        super(context, R.style.CommonMyDialog);
        if (TextUtils.isEmpty(text)) {
            this.text = context.getString(R.string.common_label_progress_message);
        } else {
            this.text = text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_progress);
        mTvText = findViewById(R.id.text);
        mTvText.setText(text);
    }

    public void setText(String text) {
        this.text = text;
        if (null != mTvText) {
            mTvText.setText(this.text);
        }
    }
}
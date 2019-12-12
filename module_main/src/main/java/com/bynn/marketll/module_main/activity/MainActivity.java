package com.bynn.marketll.module_main.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bynn.common.R2;
import com.bynn.common.utils.ToastUtils;
import com.bynn.marketll.module_main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.text) TextView text;

    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.text)
    public void click() {
        ToastUtils.showShort(this, String.valueOf(count++));
    }
}

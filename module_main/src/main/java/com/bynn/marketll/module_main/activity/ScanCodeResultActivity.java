package com.bynn.marketll.module_main.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.MainRoutePath;
import com.bynn.common.base.BaseWebActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(path = MainRoutePath.SCAN_CODE_RESULT_ACTIVITY)
public class ScanCodeResultActivity extends BaseWebActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String str = getIntent().getStringExtra("data");
        if (isHttpUrl(str)) {
            loadUrl(str);
        } else {
            loadData(str, "text/html", "utf-8");
        }
    }

    public boolean isHttpUrl(String urls) {
        boolean isUrl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
        //对比
        Pattern pat = Pattern.compile(regex.trim());
        Matcher mat = pat.matcher(urls.trim());
        //判断是否匹配
        isUrl = mat.matches();
        if (isUrl) {
            isUrl = true;
        }
        return isUrl;
    }
}

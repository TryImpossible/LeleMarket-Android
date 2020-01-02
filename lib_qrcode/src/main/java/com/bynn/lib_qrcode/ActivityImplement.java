package com.bynn.lib_qrcode;


import android.os.Handler;

import com.google.zxing.Result;

/**
 *
 */
public interface ActivityImplement {

    Handler getHandler();

    void handleDecode(Result result);
}

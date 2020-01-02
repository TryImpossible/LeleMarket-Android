package com.bynn.lib_qrcode;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

/**
 *
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

    private final CountDownLatch handlerInitLatch;
	ActivityImplement activity;
    private Handler handler;

	DecodeThread(ActivityImplement activity) {
		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}

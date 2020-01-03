package com.bynn.marketll.module_main.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.arouter.MainRoutePath;
import com.bynn.common.base.BaseActivity;
import com.bynn.common.utils.BitmapUtils;
import com.bynn.common.view.HeaderView;
import com.bynn.lib_qrcode.ActivityImplement;
import com.bynn.lib_qrcode.BeepManager;
import com.bynn.lib_qrcode.CameraManager;
import com.bynn.lib_qrcode.CaptureActivityHandler;
import com.bynn.lib_qrcode.RxQrBarTool;
import com.bynn.lib_qrcode.decoding.InactivityTimer;
import com.bynn.marketll.module_main.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@Route(path = MainRoutePath.SCAN_CODE_ACTIVITY)
public class ScanCodeActivity extends BaseActivity implements ActivityImplement {

    private static final int REQUEST_FOR_PHOTO = 123;

    @BindView(R.id.cl_container)      ConstraintLayout mClContainer;
    @BindView(R.id.header_view)       HeaderView       mHeaderView;
    @BindView(R.id.surface_view)      SurfaceView      mSurfaceView;
    @BindView(R.id.rl_capture_layout) RelativeLayout   mRlCaptureLayout;
    @BindView(R.id.iv_capture_line)   ImageView        mIvCaptureLine;
    @BindView(R.id.btn_scan_code)     Button           mBtnScanCode;
    @BindView(R.id.btn_album)         Button           mBtnAlbum;

    private InactivityTimer        mInactivityTimer;
    private CaptureActivityHandler mHandler;//扫描处理
    private BeepManager            mBeepManager;

    // 扫描边界的宽度
    private int                mCaptureWidth  = 0;
    // 扫描边界的高度
    private int                mCaptureHeight = 0;
    // 是否有预览
    private boolean            mHasSurface;
    // 是否开启闪光灯
    private boolean            mFlashing      = true;
    // 位移动画
    private TranslateAnimation mAnimation;
    // 是否开启相机权限
    private boolean            isCameraPer;
    // 是否开启SD卡权限
    private boolean            isWritePer;

    private List<Disposable> mDisposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_scan_code);
        translucentStatusBar();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isCameraPer) {
            startAnim();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAnim();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder holder = mSurfaceView.getHolder();
        if (mHasSurface) {
            initCamera(holder);
        } else {
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    if (!mHasSurface) {
                        mHasSurface = true;
                        initCamera(holder);
                    }
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mHasSurface = false;
                }
            });
        }
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.quitSynchronously();
            mHandler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInactivityTimer.shutdown();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(this);
        }
        if (mBeepManager != null) {
            mBeepManager.close();
            mBeepManager = null;
        }
        if (mDisposables != null && mDisposables.size() > 0) {
            for (Disposable disposable : mDisposables) {
                disposable.dispose();
            }
            mDisposables = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_FOR_PHOTO) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            String path = selectList.get(0).getPath();
            Result rawResult = null;
            Bitmap bitmap = null;
            try {
                bitmap = BitmapUtils.getBitmap(path, 800, 800);
                // 开始对图像资源解码
                rawResult = RxQrBarTool.decodeFromPhoto(bitmap);
                // 多识别一次
                if (rawResult == null) {
                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    bitmap = BitmapUtils.getBitmap(path, 1200, 1200);
                    rawResult = RxQrBarTool.decodeFromPhoto(bitmap);
                }
                if (rawResult != null) {
                    // 成功
                    handleDecode(rawResult);
                } else {
                    // 失败
                    showToast(R.string.main_toast_scan_fail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void handleDecode(Result result) {
        mInactivityTimer.onActivity();
        if (mBeepManager == null) {
            mBeepManager = new BeepManager(this);
        }
        mBeepManager.playBeepSoundAndVibrate();

        BarcodeFormat type = result.getBarcodeFormat();

        String qrString = result.getText();
        showToast(qrString);
        ARouter.getInstance()
                .build(MainRoutePath.SCAN_CODE_RESULT_ACTIVITY)
                .withString("data", qrString)
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });

        //2秒后重新开始识别
        mHandler.sendEmptyMessageDelayed(R.id.restart_preview, 2000);
    }

    @OnClick(R.id.btn_album)
    public void openAlbum() {
        Disposable disposable = new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector.create(ScanCodeActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .isCamera(false)
                                    .maxSelectNum(1)
                                    .forResult(REQUEST_FOR_PHOTO);
                        } else {
                            showToast(R.string.main_toast_permission_sd);
                        }
                    }
                });
        mDisposables.add(disposable);
    }

    private void initView() {
        CameraManager.init(this);
        mHasSurface = false;
        mInactivityTimer = new InactivityTimer(this);
        mDisposables = new ArrayList<>();
        RxPermissions rxPermissions = new RxPermissions(this);
        isCameraPer = rxPermissions.isGranted(Manifest.permission.CAMERA);
        if (!isCameraPer) {
            showToast(R.string.main_toast_permission_camera);
        }

        isWritePer = rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!isWritePer) {
            showToast(R.string.main_toast_permission_sd);
        }
        if (!isCameraPer || !isWritePer) {
            Disposable disposable = rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted && permission.name == Manifest.permission.CAMERA) {
                                startAnim();
                            }
                        }
                    });
            mDisposables.add(disposable);
        }
        mHeaderView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startAnim() {
        if (mAnimation == null) {
            mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                    0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
            mAnimation.setDuration(4500);
            mAnimation.setRepeatCount(-1);
            mAnimation.setRepeatMode(Animation.RESTART);
        }
        mIvCaptureLine.startAnimation(mAnimation);
    }

    private void stopAnim() {
        if (mAnimation != null) {
            mAnimation.cancel();
        }
        mIvCaptureLine.clearAnimation();
    }

    private void initCamera(SurfaceHolder holder) {
        try {
            CameraManager.get().openDriver(holder);
            Point point = CameraManager.get().getCameraResolution();
            AtomicInteger width = new AtomicInteger(point.x);
            AtomicInteger height = new AtomicInteger(point.y);
            int captureWidth = mRlCaptureLayout.getWidth() * width.get() / mClContainer.getWidth();
            int captureHeight = mRlCaptureLayout.getHeight() * height.get() / mClContainer.getHeight();
            setCaptureWidth(captureWidth);
            setCaptureHeight(captureHeight);
        } catch (IOException | RuntimeException e) {
            return;
        }
        if (mHandler == null) {
            mHandler = new CaptureActivityHandler(this);
        }
    }

    private void setCaptureWidth(int width) {
        this.mCaptureWidth = width;
        CameraManager.FRAME_WIDTH = width;
    }

    private int getCaptureWidth() {
        return mCaptureWidth;
    }

    private void setCaptureHeight(int height) {
        this.mCaptureHeight = height;
        CameraManager.FRAME_HEIGHT = height;
    }

    private int getCaptureHeight() {
        return mCaptureHeight;
    }

    private void light() {
        if (mFlashing) {
            mFlashing = false;
            // 开启闪光灯
            CameraManager.get().openLight();
        } else {
            mFlashing = true;
            // 关闭闪光灯
            CameraManager.get().offLight();
        }
    }

    private void scanError(String msg) {
        stopAnim();
    }

    private void rescan() {
        startAnim();
        mHandler.sendEmptyMessage(R.id.restart_preview);
    }
}

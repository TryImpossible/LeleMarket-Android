package com.bynn.common.base;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class BaseApplication extends TinkerApplication {
    public BaseApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.bynn.common.base.BaseApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}

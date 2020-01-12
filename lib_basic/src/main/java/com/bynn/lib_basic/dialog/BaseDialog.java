package com.bynn.lib_basic.dialog;

import androidx.fragment.app.DialogFragment;
import butterknife.Unbinder;

public class BaseDialog extends DialogFragment {

    protected Unbinder mUnbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }
}

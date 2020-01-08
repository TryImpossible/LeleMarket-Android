package com.bynn.module_database;

import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.interfaces.IModuleApplication;

import io.realm.Realm;

public class DatabaseApplication implements IModuleApplication {

    public static Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    @Override
    public void onCreate(BaseApplication application) {
        Realm.init(application);
        BaseRealm.setDefaultConfiguration();
    }
}

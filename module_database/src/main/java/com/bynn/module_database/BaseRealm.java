package com.bynn.module_database;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class BaseRealm {
    private static final String TAG = BaseRealm.class.getSimpleName();
    private static final String REALM_NAME = "baseRealm.realm";
    private static final int REALM_VERSION = 1;

    public static void setDefaultConfiguration() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(REALM_VERSION)
                .migration(new Migration())
                // migration 与 delete 有冲突， 会优先执行delete
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static class Migration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Migration;
        }

        @Override
        public int hashCode() {
            return REALM_VERSION;
        }
    }
}

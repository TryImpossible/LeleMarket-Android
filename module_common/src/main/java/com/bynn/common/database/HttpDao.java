package com.bynn.common.database;

import com.bynn.common.base.BaseApplication;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

@Data
public class HttpDao extends RealmObject {
    @PrimaryKey
    private String id;
    private String data;

    public static void insertOrUpdate(String id, Object object) {
        BaseApplication.getRealm()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        HttpDao dao = new HttpDao();
                        dao.setId(id);
                        dao.setData(BaseApplication.getGson().toJson(object));
                        realm.insertOrUpdate(dao);
                    }
                });
    }

    public static <T> T get(String id, Class<T> clazz) {
        HttpDao dao = BaseApplication.getRealm()
                .where(HttpDao.class)
                .equalTo("id", id)
                .findFirst();
        return BaseApplication.getGson().fromJson(dao.getData(), clazz);
    }
}

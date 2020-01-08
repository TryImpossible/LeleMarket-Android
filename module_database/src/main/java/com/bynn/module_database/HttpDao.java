package com.bynn.module_database;

import com.bynn.lib_basic.BaseApplication;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HttpDao extends RealmObject {
    @PrimaryKey
    public String id;
    public String key;
    public String value;

    /**
     * 添加缓存
     *
     * @param id   Http接口路径
     * @param bean Http返回数据
     * @param <T>
     */
    public static <T> void setCache(String id, T bean) {
        Realm realm = DatabaseApplication.getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                HttpDao dao = new HttpDao();
                dao.id = id;
                dao.key = bean.getClass().getSimpleName();
                dao.value = BaseApplication.getGson().toJson(bean);
                realm.insertOrUpdate(dao);
            }
        });
    }

    /**
     * 获取缓存
     *
     * @param id    Http接口路径
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getCache(String id, Class<T> clazz) {
        HttpDao dao = DatabaseApplication.getRealm()
                .where(HttpDao.class)
                .equalTo("id", id)
                .findFirst();
        if (dao != null) {
            if (clazz.getSimpleName().equals(dao.key)) {
                return BaseApplication.getGson().fromJson(dao.value, clazz);
            }
        }
        return null;
    }
}

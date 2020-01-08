package com.bynn.module_database;

import com.bynn.lib_basic.BaseApplication;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HttpDao extends RealmObject {
    private static final String FIELD__ID = "id";

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
    public static <T> Observable<Boolean> setCache(String id, T bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Realm realm = DatabaseApplication.getRealm();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        HttpDao dao = new HttpDao();
                        dao.id = id;
                        dao.key = bean.getClass().getSimpleName();
                        dao.value = BaseApplication.getGson().toJson(bean);
                        realm.insertOrUpdate(dao);
                        emitter.onNext(true);
                    }
                });
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
    public static <T> Observable<T> getCache(String id, Class<T> clazz) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                HttpDao dao = DatabaseApplication.getRealm()
                        .where(HttpDao.class)
                        .equalTo(FIELD__ID, id)
                        .findFirst();
                if (dao != null) {
                    if (clazz.getSimpleName().equals(dao.key)) {
                        Gson gson = BaseApplication.getGson();
                        emitter.onNext(gson.fromJson(dao.value, clazz));
                    }
                }
            }
        });
    }
}

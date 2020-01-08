package com.bynn.module_database;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RecentSearchDao extends RealmObject {

    @Required
    @PrimaryKey
    public String name;

    public static Observable<Boolean> insertOrUpdate(String name) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Realm realm = DatabaseApplication.getRealm();
                // 事务操作
//        realm.beginTransaction();
//        RecentSearchDao dao = realm.createObject(RecentSearchDao.class);
//        dao.name = name;
//        realm.commitTransaction();

                // 使用事务块
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RecentSearchDao dao = new RecentSearchDao();
                        dao.name = name;
                        realm.insertOrUpdate(dao);
                        emitter.onNext(true);
                    }
                });
            }
        });
    }

    public static Observable<List<String>> findAll() {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                Realm realm = DatabaseApplication.getRealm();
                RealmResults<RecentSearchDao> results = realm.where(RecentSearchDao.class).findAll();
                // Realm.copyFromRealm(dogs)方法将它转为List<T>
                if (results == null || results.size() == 0) {
                    return;
                }
                List<String> list = new ArrayList<>();
                for (RecentSearchDao dao : results) {
                    list.add(dao.name);
                }
                emitter.onNext(list);
            }
        });
    }

    public static Observable<Boolean> deleteAll() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Realm realm = DatabaseApplication.getRealm();
                final RealmResults<RecentSearchDao> results = realm.where(RecentSearchDao.class).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        boolean result = results.deleteAllFromRealm();
                        emitter.onNext(result);
                    }
                });
            }
        });
    }
}

package com.bynn.marketll.module_main.database;

import com.bynn.lib_basic.BaseApplication;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class HistorySearchDao extends RealmObject {

    @PrimaryKey
    public String name;

    public static void insertOrUpdate(String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                HistorySearchDao dao = new HistorySearchDao();
                dao.name = name;
                realm.insertOrUpdate(dao);
            }
        });
    }

    public static Observable<List<String>> findAll() {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                RealmResults<HistorySearchDao> result = BaseApplication.getRealm().where(HistorySearchDao.class).findAll();
                if (result != null || result.size() == 0) {
                    return;
                }
                List<String> list = new ArrayList<>();
                for (HistorySearchDao dao : result) {
                    list.add(dao.name);
                }
                emitter.onNext(list);
            }
        });
    }

    public static void deleteAll() {
        Realm realm = BaseApplication.getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(HistorySearchDao.class).findAll().deleteAllFromRealm();
            }
        });
    }
}

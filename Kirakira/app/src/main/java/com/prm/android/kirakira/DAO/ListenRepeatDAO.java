package com.prm.android.kirakira.DAO;

import com.prm.android.kirakira.Model.ListenRepeatContentModel;
import com.prm.android.kirakira.Model.ListenRepeatModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenRepeatDAO {
    private Realm realm;

    public ListenRepeatDAO() {
        realm = Realm.getDefaultInstance();
    }

    private static ListenRepeatDAO inst;

    public static ListenRepeatDAO getInst() {
        if (inst == null) {
            inst = new ListenRepeatDAO();
        }
        return inst;
    }
    // add content
    public void addListenRepeatPractice(ListenRepeatModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public void addListenRepeatContentPractice(ListenRepeatContentModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public List<ListenRepeatModel> getAllListenRepeatPractices() {
        return realm.where(ListenRepeatModel.class).findAll();
    }

    public List<ListenRepeatContentModel> getAllListenRepeatContentsPractice(int byListenRepeatId) {
        return realm.where(ListenRepeatContentModel.class).equalTo("listenRepeatId", byListenRepeatId).findAll();
    }

    public ListenRepeatModel getListenRepeatPracticeByID(int id) {
        return realm.where(ListenRepeatModel.class).equalTo("id",id).findFirst();
    }
}

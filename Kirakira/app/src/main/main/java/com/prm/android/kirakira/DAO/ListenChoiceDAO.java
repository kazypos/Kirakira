package com.prm.android.kirakira.DAO;

import com.prm.android.kirakira.Model.ListenChoiceContentModel;
import com.prm.android.kirakira.Model.ListenChoiceModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenChoiceDAO {
    private Realm realm;

    public ListenChoiceDAO() {
        realm = Realm.getDefaultInstance();
    }

    private static ListenChoiceDAO inst;

    public static ListenChoiceDAO getInst() {
        if (inst == null) {
            inst = new ListenChoiceDAO();
        }
        return inst;
    }
    // add content
    public void addListenChoicePractice(ListenChoiceModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public void addListenChoiceContentPractice(ListenChoiceContentModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    // get content
    public List<ListenChoiceModel> getAllListenChoicePractices() {
        return realm.where(ListenChoiceModel.class).findAllSorted("id");
    }

    public List<ListenChoiceContentModel> getAllListenChoiceContentPractices(int listenChoiceId) {
        return realm.where(ListenChoiceContentModel.class).equalTo("listenChoiceId",listenChoiceId).findAllSorted("id");
    }

    public ListenChoiceModel getListenChoicePracticeByID(int id) {
        return realm.where(ListenChoiceModel.class).equalTo("id",id).findFirst();
    }
    public ListenChoiceContentModel getListenChoicePracticeContentByID(int id) {
        return realm.where(ListenChoiceContentModel.class).equalTo("id",id).findFirst();
    }
}

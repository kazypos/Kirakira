package com.prm.android.kirakira.DAO;

import com.prm.android.kirakira.Model.FillBlankContentModel;
import com.prm.android.kirakira.Model.FillBlankModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by kazy on 7/5/2017.
 */

public class FillBlankDAO {
    private Realm realm;

    public FillBlankDAO() {
        realm = Realm.getDefaultInstance();
    }

    private static FillBlankDAO inst;

    public static FillBlankDAO getInst() {
        if (inst == null) {
            inst = new FillBlankDAO();
        }
        return inst;
    }
    // add content
    public void addFillBlankPractice(FillBlankModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public void addFillBlankContentPractice(FillBlankContentModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    // get content
    public List<FillBlankModel> getAllFillBlankPractices() {
        return realm.where(FillBlankModel.class).findAllSorted("id");
    }

    public List<FillBlankContentModel> getAllFillBlankContentPractices(int fillBlankId) {
        return realm.where(FillBlankContentModel.class).equalTo("fillBlankId",fillBlankId).findAllSorted("id");
    }

    public FillBlankModel getFillBlankPracticeByID(int id) {
        return realm.where(FillBlankModel.class).equalTo("id",id).findFirst();
    }
    public FillBlankContentModel getFillBlankContentPracticeByID(int id) {
        return realm.where(FillBlankContentModel.class).equalTo("id",id).findFirst();
    }
}

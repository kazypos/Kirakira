package com.prm.android.kirakira.DAO;

import com.prm.android.kirakira.Model.LessonModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by kazy on 7/6/2017.
 */

public class LessonDAO {
    private Realm realm;

    public LessonDAO() {
        realm = Realm.getDefaultInstance();
    }

    private static LessonDAO inst;

    public static LessonDAO getInst() {
        if (inst == null) {
            return new LessonDAO();
        }
        return inst;
    }
    // add content
    public void addLesson(LessonModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    // get content
    public List<LessonModel> getAllLessons() {
        return realm.where(LessonModel.class).findAllSorted("id");
    }


    public LessonModel getLessonByID(int id) {
        return realm.where(LessonModel.class).equalTo("id",id).findFirst();
    }

}

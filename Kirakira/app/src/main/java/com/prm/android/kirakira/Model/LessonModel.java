package com.prm.android.kirakira.Model;

import io.realm.RealmObject;

/**
 * Created by kazy on 7/5/2017.
 */

public class LessonModel extends RealmObject {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

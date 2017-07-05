package com.prm.android.kirakira.Model;

import io.realm.RealmObject;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenChoice extends RealmObject {
    private int id;
    private double time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}

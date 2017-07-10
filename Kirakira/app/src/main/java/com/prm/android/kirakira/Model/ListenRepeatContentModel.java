package com.prm.android.kirakira.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kazy on 7/10/2017.
 */

public class ListenRepeatContentModel  extends RealmObject {
    @PrimaryKey
    private int id;
    private int listenRepeatId;
    private String text;

    public ListenRepeatContentModel() {
    }

    public ListenRepeatContentModel(int id, int listenRepeatId, String text) {
        this.id = id;
        this.listenRepeatId = listenRepeatId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListenRepeatId() {
        return listenRepeatId;
    }

    public void setListenRepeatId(int listenRepeatId) {
        this.listenRepeatId = listenRepeatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

package com.prm.android.kirakira.Model;

import io.realm.RealmObject;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenChoiceContent extends RealmObject {
    private int id;
    private int listenChoiceId;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListenChoiceId() {
        return listenChoiceId;
    }

    public void setListenChoiceId(int listenChoiceId) {
        this.listenChoiceId = listenChoiceId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

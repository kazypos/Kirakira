package com.prm.android.kirakira.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenChoiceContentModel extends RealmObject {
    @PrimaryKey
    private int id;
    private int listenChoiceId;
    private String text;

    public ListenChoiceContentModel() {
    }

    public ListenChoiceContentModel(int id, int listenChoiceId, String text) {
        this.id = id;
        this.listenChoiceId = listenChoiceId;
        this.text = text;
    }

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

    @Override
    public String toString() {
        return text;
    }
}

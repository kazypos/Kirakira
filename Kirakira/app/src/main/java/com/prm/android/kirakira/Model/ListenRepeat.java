package com.prm.android.kirakira.Model;

import io.realm.RealmObject;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenRepeat extends RealmObject {
    private int id;
    private String question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

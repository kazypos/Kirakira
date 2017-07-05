package com.prm.android.kirakira.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kazy on 7/5/2017.
 */

public class ListenRepeatModel extends RealmObject {
    @PrimaryKey
    private int id;
    private int level;
    private String question;

    public ListenRepeatModel() {
    }

    public ListenRepeatModel(int id, int level, String question) {
        this.id = id;
        this.level = level;
        this.question = question;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

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

    @Override
    public String toString() {
        return id+"|"+level+"|"+question;
    }
}

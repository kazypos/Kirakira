package com.prm.android.kirakira.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kazy on 7/5/2017.
 */

public class FillBlankContentModel extends RealmObject {
    @PrimaryKey
    private int id;
    private int fillBlankId;
    private String question;
    private String answer;

    public FillBlankContentModel() {
    }

    public FillBlankContentModel(int id, int fillBlankId, String question, String answer) {
        this.id = id;
        this.fillBlankId = fillBlankId;
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFillBlankId() {
        return fillBlankId;
    }

    public void setFillBlankId(int fillBlankId) {
        this.fillBlankId = fillBlankId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return id+"|"+fillBlankId+"|"+question+"|"+answer;
    }
}

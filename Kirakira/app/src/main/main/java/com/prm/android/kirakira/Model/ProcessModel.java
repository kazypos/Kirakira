package com.prm.android.kirakira.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kazy on 7/5/2017.
 */

public class ProcessModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String userId;
    private int lessonId;
    private double progress;
}

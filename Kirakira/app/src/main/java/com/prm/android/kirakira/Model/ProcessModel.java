package com.prm.android.kirakira.Model;

import io.realm.RealmObject;

/**
 * Created by kazy on 7/5/2017.
 */

public class ProcessModel extends RealmObject {
    private int id;
    private String userId;
    private int lessonId;
    private double progress;
}

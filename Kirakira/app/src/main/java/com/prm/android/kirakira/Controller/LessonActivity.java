package com.prm.android.kirakira.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Toast;

import com.prm.android.kirakira.DAO.LessonDAO;
import com.prm.android.kirakira.Model.LessonModel;
import com.prm.android.kirakira.R;

import java.util.List;

public class LessonActivity extends Activity implements View.OnClickListener{
    LessonDAO lessonDAO;
    List<LessonModel> listLesson;

    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        layout = (ConstraintLayout) findViewById(R.id.lessonScreenLayout);
        layout.setBackgroundResource(R.drawable.background);

        lessonDAO = LessonDAO.getInst();

        listLesson = lessonDAO.getAllLessons();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int widgetId = v.getId();
        switch (widgetId) {
            case R.id.buttonListenRepeat:
                intent.setClass(this, ListenAndRepeatActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonListenChoice:
                Toast.makeText(this, "This lesson is developing.", Toast.LENGTH_SHORT).show();
//                intent.setClass(this, ListenAndRepeatActivity.class);
//                startActivity(intent);
                break;
            case R.id.buttonFillBlank:
                intent.setClass(this, Lesson8Activity.class);
                startActivity(intent);
                break;
        }
    }
}

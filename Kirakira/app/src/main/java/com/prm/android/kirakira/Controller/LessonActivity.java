package com.prm.android.kirakira.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prm.android.kirakira.DAO.LessonDAO;
import com.prm.android.kirakira.Model.LessonModel;
import com.prm.android.kirakira.R;

import java.util.List;

public class LessonActivity extends Activity {
    LessonDAO lessonDAO;
    List<LessonModel> listLesson;

    ConstraintLayout layout;

    Button btnListenRepeat,btnListenChoice,btnFillBlank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        layout = (ConstraintLayout) findViewById(R.id.lessonScreenLayout);
        layout.setBackgroundResource(R.drawable.background);

        lessonDAO = LessonDAO.getInst();

        listLesson = lessonDAO.getAllLessons();

        btnListenRepeat = (Button) findViewById(R.id.buttonListenRepeat);
        btnListenChoice = (Button) findViewById(R.id.buttonListenChoice);
        btnFillBlank = (Button) findViewById(R.id.buttonFillBlank);

        btnListenRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoPracticeView(0);
            }
        });

        btnListenChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoPracticeView(1);
            }
        });

        btnFillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoPracticeView(2);
            }
        });

    }

    void gotoPracticeView(int type){
        if  (type == 1){
            Toast.makeText(this, "This function is developing.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setClass(LessonActivity.this, PracticeActivity.class);
        intent.putExtra("LESSON_TYPE",type);
        startActivity(intent);
    }

}

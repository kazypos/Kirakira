package com.prm.android.kirakira.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.prm.android.kirakira.DAO.FillBlankDAO;
import com.prm.android.kirakira.DAO.ListenChoiceDAO;
import com.prm.android.kirakira.DAO.ListenRepeatDAO;
import com.prm.android.kirakira.Model.FillBlankModel;
import com.prm.android.kirakira.Model.ListenChoiceModel;
import com.prm.android.kirakira.Model.ListenRepeatModel;
import com.prm.android.kirakira.R;

import java.util.List;

public class PracticeActivity extends Activity {
    ListenRepeatDAO listenRepeatDAO;
    ListenChoiceDAO listenChoiceDAO;
    FillBlankDAO fillBlankDAO;
    List<ListenRepeatModel> practiceListenRepeats;
    List<ListenChoiceModel> practiceListenChoices;
    List<FillBlankModel> practiceFillBlanks;
    String[] listContent;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listent_repeat_practice);
        int count = 0;
        final int type =getIntent().getIntExtra("LESSON_TYPE",0);
        switch (type){
            case 0:
                listenRepeatDAO = ListenRepeatDAO.getInst();
                practiceListenRepeats = listenRepeatDAO.getAllListenRepeatPractices();
                count = practiceListenRepeats.size();
                break;
            case 1:
                listenChoiceDAO = ListenChoiceDAO.getInst();
                practiceListenChoices = listenChoiceDAO.getAllListenChoicePractices();
                count = practiceListenChoices.size();
                break;
            case 2:
                fillBlankDAO = FillBlankDAO.getInst();
                practiceFillBlanks = fillBlankDAO.getAllFillBlankPractices();
                count = practiceFillBlanks.size();
                break;
        }

        listContent = new String[count];
        for (int i = 0; i < count; i++) {
            listContent[i] = "Practice "+(i+1);
        }


        listView = (ListView) findViewById(R.id.listViewPractices);

        listView.setBackgroundResource(R.drawable.background);

        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoContentPractice(type,position);

            }
        });

    }

    void gotoContentPractice(int type, int level){
        Intent intent = new Intent();

        intent.putExtra("LEVEL",level);

        switch (type){
            case 0:
                intent.setClass(PracticeActivity.this, ListenAndRepeatActivity.class);
                break;
            case 1:
                intent.setClass(PracticeActivity.this, Lesson4Activity.class);
                break;
            case 2:
                intent.setClass(PracticeActivity.this, Lesson8Activity.class);
                break;
        }

        startActivity(intent);
    }
}

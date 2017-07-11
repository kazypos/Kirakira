package com.prm.android.kirakira.Controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prm.android.kirakira.DAO.ListenChoiceDAO;
import com.prm.android.kirakira.Model.AsyncTaskProcessTime;
import com.prm.android.kirakira.Model.ListenChoiceContentModel;
import com.prm.android.kirakira.R;
import com.prm.android.kirakira.Utility.MediaUtil;

import java.util.ArrayList;
import java.util.List;

public class Lesson4Activity extends Activity implements View.OnClickListener, ListView.OnItemClickListener {

    private static final int TOTAL_ANS = 5;
    private static final int TOTAL_TIMEDOWN = 30;

    private ListView listView;

    private List<ListenChoiceContentModel> choiceContentModels;
    private int level;
    private String correctText;
    private MediaUtil mediaUtil;
    private TextView tvResult;

    private void assignCorrectAnswer() {
        String newAns;
        do{
            newAns = choiceContentModels.get((int) (Math.random() * choiceContentModels.size())).getText();
        }while (newAns.equalsIgnoreCase(correctText));
        correctText = newAns;
        mediaUtil.textToSpeech(correctText);
        tvResult.setText(correctAns + "/" + totalAns);
    }

    private void initData() {
        mediaUtil = new MediaUtil(this);
        findViewById(R.id.imageButtonPlay).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.lstLesson4Answer);
        listView.setEnabled(false);
        listView.setOnItemClickListener(this);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setVisibility(View.INVISIBLE);
        tvResult.setFocusable(false);

        level = getIntent().getIntExtra("listenChoiceLevel", 0);
    }

    private void populateListView() {
        listView.setEnabled(true);
        choiceContentModels = new ArrayList<>(ListenChoiceDAO.getInst()
                .getAllListenChoiceContentPractices(level));
        while (choiceContentModels.size() > TOTAL_ANS) {
            int pos = (int) (Math.random() * choiceContentModels.size());
            ListenChoiceContentModel contentModel = choiceContentModels.get(pos);
            choiceContentModels.remove(contentModel);
        }
        assignCorrectAnswer();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choiceContentModels));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson4);

        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonPlay:
                populateListView();
                AsyncTaskProcessTime taskProcessTime = new AsyncTaskProcessTime(this, choiceContentModels);
                taskProcessTime.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, TOTAL_TIMEDOWN);
                findViewById(R.id.imageButtonPlay).setEnabled(false);
                break;
        }
    }

    private int correctAns = 0;
    private int totalAns = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(correctText)) {
            Toast.makeText(this, "correct!", Toast.LENGTH_SHORT).show();
            assignCorrectAnswer();
            correctAns++;
            totalAns++;
        } else {
            Toast.makeText(this, "wrong!", Toast.LENGTH_SHORT).show();
            assignCorrectAnswer();
            totalAns++;
        }
        tvResult.setText(correctAns + "/" + totalAns);
    }
}

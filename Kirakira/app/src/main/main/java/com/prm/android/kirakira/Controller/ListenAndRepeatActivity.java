package com.prm.android.kirakira.Controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.prm.android.kirakira.DAO.AuthenticationDAO;
import com.prm.android.kirakira.DAO.ListenRepeatDAO;
import com.prm.android.kirakira.Model.ListenRepeatModel;
import com.prm.android.kirakira.Model.UserModel;
import com.prm.android.kirakira.R;
import com.prm.android.kirakira.Utility.AsyncTaskPlayReorder;
import com.prm.android.kirakira.Utility.MediaUtil;

import java.util.List;

public class ListenAndRepeatActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {
    private Button btnStartRecord;
    private Button btnplayRecord;
    private Drawable startRecord;
    private Drawable stopRecord;
    private Drawable startReplay;
    private Drawable stopReplay;

    private MediaUtil mediaUtility;

    private void initData() {
        startRecord = getResources().getDrawable(R.drawable.startrecord);
        stopRecord = getResources().getDrawable(R.drawable.stoprecord);
        startReplay = getResources().getDrawable(R.drawable.startreplay);
        stopReplay = getResources().getDrawable(R.drawable.stopreplay);

        mediaUtility = new MediaUtil(this);

        btnStartRecord = (Button) findViewById(R.id.btnStartRecord);
        btnStartRecord.setOnClickListener(this);
        btnStartRecord.setBackground(startRecord);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        Intent intent = getIntent();

        UserModel userModel = AuthenticationDAO.getInst().getUserById(intent.getStringExtra("userId"));
        Toast.makeText(this, "Welcome " + userModel.getName(), Toast.LENGTH_SHORT).show();

        int level = intent.getIntExtra("listenLevel", 1);
        List<ListenRepeatModel> strings = ListenRepeatDAO.getInst().getAllListenRepeatByLevel(level);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));

        btnplayRecord = (Button) findViewById(R.id.btnPlayRecord);
        btnplayRecord.setOnClickListener(this);
        btnplayRecord.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_and_repeat);

        initData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String value = parent.getItemAtPosition(position).toString();
        mediaUtility.textToSpeech(value);
    }

    @Override
    public void onClick(View v) {
        int widgetId = v.getId();
        switch (widgetId) {
            case R.id.btnStartRecord:
                if (btnStartRecord.getBackground().equals(startRecord)) {
                    btnplayRecord.setEnabled(false);
                    btnplayRecord.setBackground(stopReplay);
                    mediaUtility.stopPlayRecord();
                    btnStartRecord.setBackground(stopRecord);
                    mediaUtility.mediaRecorderReady();
                    mediaUtility.startRecord();
                } else {
                    System.out.println("VISIBLE: "+btnplayRecord.getVisibility());
                    if (btnplayRecord.getVisibility() == View.INVISIBLE) btnplayRecord.setVisibility(View.VISIBLE);
                    btnplayRecord.setEnabled(true);
                    btnplayRecord.setBackground(startReplay);
                    btnStartRecord.setBackground(startRecord);
                    mediaUtility.stopRecord();
                }
                break;
            case R.id.btnPlayRecord:
                if (btnplayRecord.getBackground().equals(startReplay)) {
                    btnplayRecord.setBackground(stopReplay);
                    int duration = mediaUtility.playRecord();
                    AsyncTaskPlayReorder playReorder = new AsyncTaskPlayReorder(this, startReplay);
                    playReorder.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, duration);
                } else {
                    btnplayRecord.setBackground(startReplay);
                    mediaUtility.stopPlayRecord();
                }
                break;
        }
    }

}

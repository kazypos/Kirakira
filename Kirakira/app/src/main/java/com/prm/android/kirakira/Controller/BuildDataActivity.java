package com.prm.android.kirakira.Controller;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prm.android.kirakira.DAO.AuthenticationDAO;
import com.prm.android.kirakira.DAO.FillBlankDAO;
import com.prm.android.kirakira.DAO.LessonDAO;
import com.prm.android.kirakira.DAO.ListenChoiceDAO;
import com.prm.android.kirakira.DAO.ListenRepeatDAO;
import com.prm.android.kirakira.Model.FillBlankContentModel;
import com.prm.android.kirakira.Model.FillBlankModel;
import com.prm.android.kirakira.Model.LessonModel;
import com.prm.android.kirakira.Model.ListenChoiceContentModel;
import com.prm.android.kirakira.Model.ListenChoiceModel;
import com.prm.android.kirakira.Model.ListenRepeatModel;
import com.prm.android.kirakira.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.realm.Realm;


public class BuildDataActivity extends AppCompatActivity {
    Button btnShow;
    EditText editTextShow;
    ListenRepeatDAO listenRepeatDAO;
    ListenChoiceDAO listenChoiceDAO;
    FillBlankDAO fillBlankDAO;
    LessonDAO lessonDAO;



    AuthenticationDAO authenticationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_data);

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });

        editTextShow = (EditText) findViewById(R.id.editTextShow);
        btnShow = (Button) findViewById(R.id.buttonShow);

        lessonDAO = LessonDAO.getInst();

        listenRepeatDAO =  ListenRepeatDAO.getInst();
        listenChoiceDAO =  ListenChoiceDAO.getInst();
        fillBlankDAO =  FillBlankDAO.getInst();

//        authenticationDAO = AuthenticationDAO.getInst();

//        UserModel user = authenticationDAO.register(username, pass, name);
//        if (user != null){
//            editTextShow.append("dang ky"+user.toString()+"\n");
//        } else {
//            editTextShow.append("thong tin khong phu hop\n");
//        }

        btnShow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                addLesson();
//                showLesson();
//                addListenRepeat();
//                showListenRepeat();
//                addListenChoice();
//                showListenChoice();
//                addFillBlank();
//                showFillBlank();

//                UserModel user = authenticationDAO.signIn(username, pass);
//                if (user != null){
//                    editTextShow.append("Dang nhap"+user.toString()+"\n");
//                } else {
//                    editTextShow.append("dang nhap that bai\n");
//                }
            }
        });
    }

    public String getContentJsonFromFile(String fileName){
        String result = "";
        try {
            InputStream fileStream = getAssets().open(fileName);
            int fileLen = 0;
            fileLen = fileStream.available();
            // Read the entire resource into a local byte buffer.
            byte[] fileBuffer = new byte[fileLen];
            fileStream.read(fileBuffer);
            fileStream.close();
            result = new String(fileBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    void addLesson(){
        try {

            String contentJson = getContentJsonFromFile("data/Lesson.json");

            JSONArray lessons = new JSONArray(contentJson);

            for (int i=0;i<lessons.length();i++){
                lessonDAO.addLesson(new LessonModel(i,lessons.get(i).toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void showLesson(){
        List<LessonModel> list = lessonDAO.getAllLessons();
        for (int i = 0; i < list.size(); i++) {
            editTextShow.append(list.get(i).toString()+"\n");
        }
    }

    void addFillBlank(){

        try {

            String contentJson = getContentJsonFromFile("data/FillBlank.json");

            JSONArray fillBlanks = new JSONArray(contentJson);
            int id = 0;
            for (int i=0;i<fillBlanks.length();i++){
                JSONObject obj = fillBlanks.getJSONObject(i);
                double time = obj.getDouble("time");
                Log.i("json data:", fillBlanks.get(i).toString());
                JSONArray fillBlankContents = obj.getJSONArray("texts");
                fillBlankDAO.addFillBlankPractice(new FillBlankModel(i,time));

                for (int j = 0; j < fillBlankContents.length(); j++) {
                    JSONObject objContent = fillBlankContents.getJSONObject(j);
                    Log.i("json object:", objContent.toString());
                    String question = objContent.getString("question");
                    String answer = objContent.getString("answer");
                    Log.i("json object content:", fillBlankContents.get(j).toString());
                    fillBlankDAO.addFillBlankContentPractice(new FillBlankContentModel(id,i,question,answer));
                    id++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void showFillBlank(){
        List<FillBlankModel> list = fillBlankDAO.getAllFillBlankPractices();
        for (int i = 0; i < list.size(); i++) {
            editTextShow.append(list.get(i).toString()+"\n");
            List<FillBlankContentModel> listContents = fillBlankDAO.getAllFillBlankContentPractices(i);
            for (int j = 0; j < listContents.size(); j++) {
                editTextShow.append("->"+listContents.get(j).toString()+"\n");
            }
        }
    }

    void addListenChoice(){
        try {

            String contentJson = getContentJsonFromFile("data/ListenChoice.json");

            JSONArray listenChoices = new JSONArray(contentJson);
            int id = 0;
            for (int i=0;i<listenChoices.length();i++){
                JSONObject obj = listenChoices.getJSONObject(i);
                double time = obj.getDouble("time");
                Log.i("json data:", listenChoices.get(i).toString());
                JSONArray listenChoiceContents = obj.getJSONArray("texts");
                listenChoiceDAO.addListenChoicePractice(new ListenChoiceModel(i,time));
                for (int j = 0; j < listenChoiceContents.length(); j++) {
                    Log.i("json object:", listenChoiceContents.get(j).toString());
                    listenChoiceDAO.addListenChoiceContentPractice(new ListenChoiceContentModel(id,i,listenChoiceContents.get(j).toString()));
                    id++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void showListenChoice(){
        List<ListenChoiceModel> list = listenChoiceDAO.getAllListenChoicePractices();
        for (int i = 0; i < list.size(); i++) {
            editTextShow.append(list.get(i).toString()+"\n");
            List<ListenChoiceContentModel> listContents = listenChoiceDAO.getAllListenChoiceContentPractices(i);
            for (int j = 0; j < listContents.size(); j++) {
                editTextShow.append("->"+listContents.get(j).toString()+"\n");
            }
        }
    }

    void addListenRepeat(){
        try {

            String contentJson = getContentJsonFromFile("data/ListentRepeat.json");

            JSONArray listenRepeats = new JSONArray(contentJson);
            int id = 0;
            for (int i=0;i<listenRepeats.length();i++){
                Log.i("json data:", listenRepeats.get(i).toString());
                JSONArray listenRepeatContents = listenRepeats.getJSONArray(i);
                for (int j = 0; j < listenRepeatContents.length(); j++) {
                    Log.i("json object:", listenRepeatContents.get(j).toString());
                    listenRepeatDAO.addListenRepeatPractice(new ListenRepeatModel(id,i+1,listenRepeatContents.get(j).toString()));
                    id++;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void showListenRepeat(){
        List<ListenRepeatModel> list = listenRepeatDAO.getAllListenRepeatPractices();
        for (int i = 0; i < list.size(); i++) {
            editTextShow.append(list.get(i).toString()+"\n");
        }
    }

}

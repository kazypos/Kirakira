package com.prm.android.kirakira.DAO;

import android.app.Application;
import android.util.Log;

import com.prm.android.kirakira.Model.FillBlankContentModel;
import com.prm.android.kirakira.Model.FillBlankModel;
import com.prm.android.kirakira.Model.LessonModel;
import com.prm.android.kirakira.Model.ListenChoiceContentModel;
import com.prm.android.kirakira.Model.ListenChoiceModel;
import com.prm.android.kirakira.Model.ListenRepeatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by kazy on 7/5/2017.
 */

public class DBConfigration extends Application {

    ListenRepeatDAO listenRepeatDAO;
    ListenChoiceDAO listenChoiceDAO;
    FillBlankDAO fillBlankDAO;
    LessonDAO lessonDAO;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        importData();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    void importData(){
        lessonDAO = LessonDAO.getInst();
        if (lessonDAO.getAllLessons().size() <= 0) {

            listenRepeatDAO =  ListenRepeatDAO.getInst();
            listenChoiceDAO =  ListenChoiceDAO.getInst();
            fillBlankDAO =  FillBlankDAO.getInst();

            addLesson();
            addFillBlank();
            addListenChoice();
            addListenRepeat();
        }
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


}

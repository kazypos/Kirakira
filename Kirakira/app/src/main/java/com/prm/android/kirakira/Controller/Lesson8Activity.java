package com.prm.android.kirakira.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.prm.android.kirakira.DAO.FillBlankDAO;
import com.prm.android.kirakira.Model.FillBlankContentModel;
import com.prm.android.kirakira.Model.FillBlankModel;
import com.prm.android.kirakira.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Lesson8Activity extends AppCompatActivity {

    FillBlankDAO fillBlankDAO;
    List<FillBlankModel> listFillBlankModel;
    List<FillBlankContentModel> listFillBlankContentModel;

    TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson8);

        //listFillBlankModel = FillBlankDAO.getInst().getAllFillBlankPractices();
        listFillBlankContentModel = FillBlankDAO.getInst().getAllFillBlankContentPractices(0);

        textViews = new TextView[4];
        Integer[] randomIndexList = random(textViews.length);
        for(int i  = 0; i < textViews.length; i++) {
            String tViewId = "tvAnswer" + (i+1);
            int id = getResources().getIdentifier(tViewId, "id", getPackageName());
            textViews[i] = ((TextView) findViewById(id));
            textViews[i].setText(listFillBlankContentModel.get(randomIndexList[i]).getAnswer().toString());
        }
    }

    private Integer[] random(int max) {
        Integer[] arr = new Integer[max];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        Toast.makeText(getApplicationContext(), Arrays.toString(arr), Toast.LENGTH_SHORT).show();
        return arr;
    }
}

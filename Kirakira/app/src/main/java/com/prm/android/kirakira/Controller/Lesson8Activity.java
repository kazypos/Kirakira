package com.prm.android.kirakira.Controller;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
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

    TextView tvBlank1;
    TextView tvBlank2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson8);

        //test
        tvBlank1 = (TextView)findViewById(R.id.tvBlank1);
        tvBlank2 = (TextView)findViewById(R.id.tvBlank2);

        int level = getIntent().getIntExtra("LEVEL",2);
        listFillBlankContentModel = FillBlankDAO.getInst().getAllFillBlankContentPractices(level);

        textViews = new TextView[4];
        Integer[] randomIndexList = random(textViews.length);
        for(int i  = 0; i < textViews.length; i++) {
            String tViewId = "tvAnswer" + (i+1);
            int id = getResources().getIdentifier(tViewId, "id", getPackageName());
            textViews[i] = ((TextView) findViewById(id));
            textViews[i].setOnTouchListener(new ChoiceTouchListener());
            textViews[i].setText(listFillBlankContentModel.get(randomIndexList[i]).getAnswer().toString());
        }

        tvBlank1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DROP: {
                        tvBlank2.setText("Droped");
                        return true;
                    }
                    case DragEvent.ACTION_DRAG_ENDED:
                        tvBlank2.setText("Ended");
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
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

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                //start dragging the item touched
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


}

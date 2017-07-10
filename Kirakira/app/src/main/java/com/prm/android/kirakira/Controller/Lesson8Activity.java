package com.prm.android.kirakira.Controller;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.prm.android.kirakira.DAO.FillBlankDAO;
import com.prm.android.kirakira.Model.FillBlankContentModel;
import com.prm.android.kirakira.Model.FillBlankModel;
import com.prm.android.kirakira.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Lesson8Activity extends AppCompatActivity {

    private Timer timer;
    private DonutProgress donutProgress;

    FillBlankDAO fillBlankDAO;
    List<FillBlankModel> listFillBlankModel;
    List<FillBlankContentModel> listFillBlankContentModel;

    TextView[] textViewsAnswers;
    TextView[] textViewsQuestions;

    TextView tvCountDown;

    ImageView imageView;

    int score = 0;
    boolean winner = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson8);

        //donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        tvCountDown = (TextView) findViewById(R.id.tvCounDown);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);

        listFillBlankContentModel = FillBlankDAO.getInst().getAllFillBlankContentPractices(0);

        new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = String.valueOf(millisUntilFinished / 1000);
                tvCountDown.setText(time);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.sad);
                imageView.setVisibility(View.VISIBLE);
                tvCountDown.setVisibility(View.INVISIBLE);
            }
        }.start();

//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        boolean a = false;
//                        if (a) {
//                            ObjectAnimator anim = ObjectAnimator.ofInt(donutProgress, "progress", 0, 20);
//                            anim.setInterpolator(new DecelerateInterpolator());
//                            anim.setIntValues(8);
//                            anim.setDuration(7000);
//                            anim.start();
//                        } else {
//                            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(Lesson8Activity.this, R.animator.progress_anim);
//                            set.setInterpolator(new DecelerateInterpolator());
//                            set.setTarget(donutProgress);
//                            set.setDuration(10000);
//                            set.start();
//                        }
//                    }
//                });
//            }
//        }, 0, 10000);

        textViewsAnswers = new TextView[listFillBlankContentModel.size()];
        Integer[] randomIndexAnswer = random(textViewsAnswers.length);
        //set answers
        for (int i = 0; i < textViewsAnswers.length; i++) {
            String tViewId = "tvAnswer" + (i + 1);
            int id = getResources().getIdentifier(tViewId, "id", getPackageName());
            textViewsAnswers[i] = ((TextView) findViewById(id));
            textViewsAnswers[i].setText(listFillBlankContentModel.get(randomIndexAnswer[i]).getAnswer().toString());
            textViewsAnswers[i].setTag(listFillBlankContentModel.get(randomIndexAnswer[i]).getId());
            textViewsAnswers[i].setOnTouchListener(new ChoiceTouchListener());
        }
        //set questions
        textViewsQuestions = new TextView[listFillBlankContentModel.size()];
        Integer[] randomIndexQuestion = random(textViewsAnswers.length);
        for (int i = 0; i < textViewsAnswers.length; i++) {
            String tViewId = "tvBlank" + (i + 1);
            int id = getResources().getIdentifier(tViewId, "id", getPackageName());
            textViewsAnswers[i] = ((TextView) findViewById(id));
            textViewsAnswers[i].setText(listFillBlankContentModel.get(randomIndexQuestion[i]).getQuestion().toString());
            textViewsAnswers[i].setTag(listFillBlankContentModel.get(randomIndexQuestion[i]).getId());
            textViewsAnswers[i].setOnDragListener(new ChoiceDragListener());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
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
                v.startDrag(data, shadowBuilder, v, 0);
                //v.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();
            TextView drop = (TextView) view;
            TextView dropTarget = (TextView) v;
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    String dropId = drop.getTag().toString();
                    String dropTargetId = dropTarget.getTag().toString();
                    if (dropId.equalsIgnoreCase(dropTargetId)) {
                        score += 1;
                        if (score == 5) {
                            imageView.setImageResource(R.drawable.smile);
                            imageView.setVisibility(View.VISIBLE);
                            winner = true;
                            score = 0;
                        } else {
                            winner = false;
                        }
                        view.setVisibility(View.INVISIBLE);
                        String complete = completeSentence(dropTarget.getText().toString(), drop.getText().toString());
                        dropTarget.setText(complete);
                        dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        view.setVisibility(View.VISIBLE);
                        drop.setVisibility(View.VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }
            return true;
        }
    }

    private String completeSentence(String question, String answer) {
        String completeQuestion = question.replace("__", answer);
        return completeQuestion;
    }


}

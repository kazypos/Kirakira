package com.prm.android.kirakira.Model;

import android.os.AsyncTask;
import android.widget.TextView;

import com.prm.android.kirakira.Controller.Lesson4Activity;
import com.prm.android.kirakira.R;
import com.prm.android.kirakira.Utility.DialogUtil;

import java.util.List;

public class AsyncTaskProcessTime extends AsyncTask<Integer, Integer, Void> {
    private Lesson4Activity context;
    private TextView textView;
    private List<ListenChoiceContentModel> list;

    public AsyncTaskProcessTime(Lesson4Activity context, List<ListenChoiceContentModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        int value = integers[0];
        for (int i = value - 1; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        textView = (TextView) context.findViewById(R.id.tvTimeCountDown);
        textView.setText(values[0] + "");
        if (values[0] == 0) {
            DialogUtil dialogUtil = new DialogUtil(context);
            dialogUtil.setButtonCorrectColor();
            TextView textView = (TextView) context.findViewById(R.id.tvResult);
            String total = textView.getText().toString();
            dialogUtil.showErrorDialog("Well done! You get: " + total + ".");
            context.findViewById(R.id.imageButtonPlay).setEnabled(true);
        }
    }
}

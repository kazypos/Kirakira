package com.prm.android.kirakira.Utility;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Button;
import com.prm.android.kirakira.Controller.ListenAndRepeatActivity;
import com.prm.android.kirakira.R;

/**
 * Created by Khue DZ on 9/7/2017.
 */
public class AsyncTaskPlayReorder extends AsyncTask<Integer, Boolean, Void> {
    private ListenAndRepeatActivity context;
    private Drawable drawable;

    public AsyncTaskPlayReorder(ListenAndRepeatActivity context, Drawable drawable) {
        this.context = context;
        this.drawable = drawable;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        try {
            int time = integers[0];
            Thread.sleep(time);
            publishProgress(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
        try {
            boolean value = values[0];
            if (value) {
                Button playRecord = (Button) context.findViewById(R.id.btnPlayRecord);
                if(!playRecord.getBackground().equals(drawable)) {
                    playRecord.setBackground(drawable);
                }
            }
        } catch (Exception e) {
        }
    }
}

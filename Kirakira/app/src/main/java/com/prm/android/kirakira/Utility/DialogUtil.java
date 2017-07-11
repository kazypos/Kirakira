package com.prm.android.kirakira.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.prm.android.kirakira.R;

/**
 * Created by Khue DZ on 6/7/2017.
 */
public class DialogUtil implements View.OnClickListener {
    private Dialog errorDialog;
    private TextView tvError;

    public DialogUtil(Context context) {
        // init dialog for error
        errorDialog = new Dialog(context);
        errorDialog.setContentView(R.layout.error_dialog);
//        errorDialog.setTitle(context.getString(R.string.dialog_error_title));
        errorDialog.findViewById(R.id.dialog_ok).setOnClickListener(this);
        tvError = (TextView) errorDialog.findViewById(R.id.dialog_info_error);
    }

    public void setButtonCorrectColor(){
        errorDialog.findViewById(R.id.dialog_ok).setBackgroundResource(R.color.green);
    }

    public void setButtonErrorColor(){
        errorDialog.findViewById(R.id.dialog_ok).setBackgroundResource(R.color.dialog_cancel_bgcolor);
    }

    public void showErrorDialog(String message) {
        tvError.setText(message);
        errorDialog.show();
    }

    @Override
    public void onClick(View v) {
        errorDialog.hide();
    }
}

package com.prm.android.kirakira.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.prm.android.kirakira.DAO.AuthenticationDAO;
import com.prm.android.kirakira.Model.UserModel;
import com.prm.android.kirakira.R;
import com.prm.android.kirakira.Utility.DialogUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        CheckBox.OnCheckedChangeListener {

    private EditText edtRegUsername;
    private EditText edtRegPassword;
    private EditText edtRegConfirmPassword;
    private EditText edtRegFullName;
    private Button btnRegister;
    private CheckBox cbLicense;

    private AuthenticationDAO dao;
    private DialogUtil dialogUtil;
    private Dialog licenseDialog;

    private void initData() {

        // init dao
        dao = AuthenticationDAO.getInst();

        // init dialog
        dialogUtil = new DialogUtil(this);

        // init license Dialog

        // init dialog for license
        licenseDialog = new Dialog(this);
        licenseDialog.setContentView(R.layout.license_dialog);
//        licenseDialog.setTitle(context.getString(R.string.dialog_license_title));
        licenseDialog.findViewById(R.id.dialog_accept).setOnClickListener(this);
        licenseDialog.findViewById(R.id.dialog_ignore).setOnClickListener(this);

        // init other buttons, edit-texts, text-views and checkbox
        edtRegConfirmPassword = (EditText) findViewById(R.id.edtRegConfirmPassword);
        edtRegFullName = (EditText) findViewById(R.id.edtRegFullname);
        edtRegPassword = (EditText) findViewById(R.id.edtRegPassword);
        edtRegUsername = (EditText) findViewById(R.id.edtRegUsername);
        cbLicense = (CheckBox) findViewById(R.id.cbRegLicense);
        cbLicense.setOnCheckedChangeListener(this);
        TextView tvLicense = (TextView) findViewById(R.id.tvRegLicense);
        tvLicense.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnRegister.setEnabled(false);
        findViewById(R.id.tvRegLogin).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        switch (v.getId()) {
            case R.id.dialog_accept:
                cbLicense.setChecked(true);
                licenseDialog.hide();
                return;
            case R.id.dialog_ignore:
                cbLicense.setChecked(false);
                licenseDialog.hide();
                return;
            case R.id.tvRegLicense:
                licenseDialog.show();
                return;
            case R.id.btnRegister:
                String user_account = edtRegUsername.getText().toString();
                String user_password = edtRegPassword.getText().toString();
                String user_confirmPassword = edtRegConfirmPassword.getText().toString();
                String user_fullname = edtRegFullName.getText().toString();
                if ((user_account.isEmpty() && user_password.isEmpty()
                        && user_confirmPassword.isEmpty() && user_fullname.isEmpty())
                        || (user_account == null && user_password == null
                        && user_fullname == null && user_confirmPassword == null)) {
                    dialogUtil.showErrorDialog(getString(R.string.dialog_error_text));
                    edtRegUsername.requestFocus();
                    return;
                }
                if (user_account == null || user_account.length() < 6) {
                    dialogUtil.showErrorDialog("Username must be greater than or equal to 8 chars!");
                    edtRegUsername.requestFocus();
                    return;
                }
                if (user_password == null || user_password.length() < 8) {
                    dialogUtil.showErrorDialog("Password must be greater than or equal to 8 chars!");
                    edtRegPassword.requestFocus();
                    return;
                }
                if (user_confirmPassword == null || user_confirmPassword.length() < 8) {
                    dialogUtil.showErrorDialog("Password must be greater than or equal to 8 chars!");
                    edtRegConfirmPassword.requestFocus();
                    return;
                }
                if (user_fullname == null || user_fullname.isEmpty()) {
                    dialogUtil.showErrorDialog("Your full name cannot be empty!");
                    edtRegFullName.requestFocus();
                    return;
                }
                if (!user_password.equals(user_confirmPassword)) {
                    dialogUtil.showErrorDialog("Your password do not match!!");
                    edtRegConfirmPassword.requestFocus();
                    return;
                }
                UserModel userModel = dao.register(user_account, user_password, user_fullname);
                if (userModel == null) {
                    dialogUtil.showErrorDialog("Your username is existed!!");
                    edtRegUsername.requestFocus();
                    return;
                }
                intent.putExtra("username", user_account);
                intent.putExtra("password", user_password);
                break;
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        btnRegister.setEnabled(isChecked);
    }
}

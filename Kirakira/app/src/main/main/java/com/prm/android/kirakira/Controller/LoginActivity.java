package com.prm.android.kirakira.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.prm.android.kirakira.DAO.AuthenticationDAO;
import com.prm.android.kirakira.Model.UserModel;
import com.prm.android.kirakira.R;
import com.prm.android.kirakira.Utility.DialogUtil;
import com.prm.android.kirakira.Utility.SharePreferencesUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AuthenticationDAO dao;
    private EditText edtUserNameLogin;
    private EditText edtPasswordLogin;
    private DialogUtil dialogUtil;

    private void genKey() {
        PackageInfo info;
        try {
            PackageManager pm = getPackageManager();
            info = pm.getPackageInfo("com.prm.android.kirakira", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            } else if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void initData() {

        // init the password edit-text
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        edtPasswordLogin.setOnClickListener(this);

        // init the username edit-text
        edtUserNameLogin = (EditText) findViewById(R.id.edtUsernameLogin);
        edtUserNameLogin.setOnClickListener(this);

        // init for other buttons or text-views
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
        findViewById(R.id.tvForgotPassword).setOnClickListener(this);

        // init error dialog
        dialogUtil = new DialogUtil(this);

        //init Authentication for user
        dao = AuthenticationDAO.getInst();

        // check current user
        UserModel userModel = SharePreferencesUtil.getCurrentUser(this);
        if (userModel != null) {
            if (dao.signIn(userModel.getUsername(), userModel.getPassword()) != null) {
                userModel = dao.getUserByUsername(userModel.getUsername());
                SharePreferencesUtil.setSharedPreferences(this,
                        userModel);
                requestToLoginSuccessLayout(userModel);
            }
        }

        Intent intent = getIntent();
        if (intent.getStringExtra("username") != null) {
            edtUserNameLogin.setText(intent.getStringExtra("username"));
            edtPasswordLogin.setText(intent.getStringExtra("password"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        genKey();
        initPermission();
        initData();
    }

    private void requestToLoginSuccessLayout(UserModel userModel) {
        Intent intent = new Intent();
        // TODO
        intent.setClass(this, ListenAndRepeatActivity.class);
        intent.putExtra("userId", userModel.getId());
        int random = (int) Math.random() * 2;
        random = random == 0 ? 1 : 2;
        intent.putExtra("listenLevel", random);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int widgetId = v.getId();
        switch (widgetId) {
            case R.id.btnLogin:
                String user_account = edtUserNameLogin.getText().toString();
                String user_password = edtPasswordLogin.getText().toString();
                if ((user_account.isEmpty() && user_password.isEmpty())
                        || (user_account == null && user_password == null)) {
                    dialogUtil.showErrorDialog(getString(R.string.dialog_error_text));
                    edtUserNameLogin.requestFocus();
                    return;
                }
                if (user_account == null || user_account.length() < 6) {
                    dialogUtil.showErrorDialog("Username must be greater than or equal to 6 chars!");
                    edtUserNameLogin.requestFocus();
                    return;
                }
                if (user_password == null || user_password.length() < 8) {
                    dialogUtil.showErrorDialog("Password must be greater than or equal to 8 chars!");
                    edtPasswordLogin.requestFocus();
                    return;
                }
                UserModel userModel = dao.signIn(user_account, user_password);
                if (userModel != null) {
                    UserModel user = new UserModel();
                    user.setUsername(user_account);
                    user.setPassword(user_password);
                    SharePreferencesUtil.setSharedPreferences(this, user);
                    requestToLoginSuccessLayout(userModel);
                }
                break;
            case R.id.tvForgotPassword:
                break;
            case R.id.tvRegister:
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}

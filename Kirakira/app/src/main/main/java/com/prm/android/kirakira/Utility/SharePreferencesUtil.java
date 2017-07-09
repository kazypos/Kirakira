package com.prm.android.kirakira.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.prm.android.kirakira.Model.UserModel;
import com.prm.android.kirakira.R;

/**
 * Created by Khue DZ on 5/7/2017.
 */
public class SharePreferencesUtil {

    public static UserModel getCurrentUser(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.my_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(context.getString(R.string.save), false)) {
            UserModel user = new UserModel();
            user.setUsername(sharedPreferences.getString(context.getString(R.string.username), ""));
            user.setPassword(sharedPreferences.getString(context.getString(R.string.password), ""));
            user.setId(sharedPreferences.getString(context.getString(R.string.id), ""));
            return user;
        }
        return null;
    }

    public static void setSharedPreferences(Context context, UserModel user) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.my_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.save), true);
        editor.putString(context.getString(R.string.id), user.getId());
        editor.putString(context.getString(R.string.username), user.getUsername());
        editor.putString(context.getString(R.string.password), user.getPassword());
        editor.apply();
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.my_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

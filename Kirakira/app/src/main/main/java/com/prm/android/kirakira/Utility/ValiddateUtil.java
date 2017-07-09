package com.prm.android.kirakira.Utility;

/**
 * Created by kazy on 7/6/2017.
 */

public class ValiddateUtil {
    public static final boolean validateUser(String username, String pass, String name){
        if (pass == "" || name == "" || username == "") return false;
        if (username.contains(" ")) return false;
        return true;
    }
}

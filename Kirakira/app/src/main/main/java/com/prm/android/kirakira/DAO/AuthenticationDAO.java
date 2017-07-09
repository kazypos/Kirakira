package com.prm.android.kirakira.DAO;

import com.prm.android.kirakira.Model.UserModel;
import com.prm.android.kirakira.Utility.CryptionUtil;
import com.prm.android.kirakira.Utility.ValiddateUtil;

import io.realm.Realm;

/**
 * Created by kazy on 7/6/2017.
 */

public class AuthenticationDAO {
    private Realm realm;

    private AuthenticationDAO() {
        realm = Realm.getDefaultInstance();
    }

    private static AuthenticationDAO inst;

    public static AuthenticationDAO getInst() {
        if (inst == null) {
            inst = new AuthenticationDAO();
        }
        return inst;
    }

    public UserModel signIn(String username, String pass) {

        if (!ValiddateUtil.validateUser(username, pass, "t k")) return null;

        String md5Pass = CryptionUtil.md5(pass);
        UserModel user = getUserByUsername(username);
        if (user == null) return null;
        if (!user.getPassword().equals(md5Pass)) return null;
        return user;
    }

    @SuppressWarnings("unused")
    public UserModel signInWithFB(String fId, String name) {
        UserModel user = getUserById(fId);
        if (user == null) {
            addUser(new UserModel(fId, "", "", name));
            user = getUserById(fId);
        }
        return user;
    }

    @SuppressWarnings("unused")
    public UserModel signInWithGG(String gId, String name) {

        UserModel user = getUserById(gId);
        if (user == null) {
            addUser(new UserModel(gId, "", "", name));
            user = getUserById(gId);
        }
        return user;

    }

    public boolean signOut() {
        return true;
    }

    public UserModel register(String username, String pass, String name) {
        if (!ValiddateUtil.validateUser(username, pass, name)) return null;

        UserModel user = getUserByUsername(username);
        if (user != null) return null;

        String id = CryptionUtil.md5(username + pass + name);
        String md5Pass = CryptionUtil.md5(pass);

        addUser(new UserModel(id, username, md5Pass, name));
        user = getUserByUsername(username);

        return user;
    }

    public UserModel getUserByUsername(String username) {
        return realm.where(UserModel.class).equalTo("username", username).findFirst();
    }

    public UserModel getUserById(String id) {
        return realm.where(UserModel.class).equalTo("id", id).findFirst();
    }

    private void addUser(UserModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
}

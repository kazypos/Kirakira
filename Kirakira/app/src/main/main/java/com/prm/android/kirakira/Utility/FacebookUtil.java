package com.prm.android.kirakira.Utility;

import android.os.Bundle;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.prm.android.kirakira.Controller.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Khue DZ on 9/7/2017.
 */
public class FacebookUtil {
    LoginActivity context;

    public FacebookUtil(LoginActivity context) {
        this.context = context;
    }

    void setFacebookData(final LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String firstName = response.getJSONObject().getString("first_name");
                            String id = response.getJSONObject().getString("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "first_name");
        bundle.putString("fields", "id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }
}

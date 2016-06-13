package com.techHome.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techHome.activities.EnterModeActivity;

/**
 * Created by Dell on 6/13/2016.
 */
public class Logout {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    public Logout(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(((Activity) this.context).getBaseContext());
        sharedEditor = sharedPreferences.edit();
    }

    public void logout() {
        sharedEditor.putBoolean("isLogin", false);
        Gson gson = new Gson();
        String json = gson.toJson(null);
        sharedEditor.putString("session", json);
        sharedEditor.putString("profileDTO", json);
        if (sharedEditor.commit()) {
            Intent intent = new Intent(context.getApplicationContext().getApplicationContext(), EnterModeActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("logout", "Logout");
            intent.putExtras(bundle);

            context.startActivity(intent);
            ((Activity) context).finish();
/*
            Toast.makeText(context.getApplicationContext().getApplicationContext(), "Logout Successful.", Toast.LENGTH_LONG).show();
*/

        }
    }
}
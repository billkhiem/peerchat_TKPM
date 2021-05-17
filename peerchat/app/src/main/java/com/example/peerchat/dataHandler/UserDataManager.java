package com.example.peerchat.dataHandler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class UserDataManager {

    static UserDataManager single_instance = null;

    public static SharedPreferences mSharedPref;


    public String username = "";
    public String avatarPath = "";
    public String pass = "";

    public UserDataManager() {
        this.username = "haha";
        this.avatarPath = "haha";
        this.pass="12345";
    }
    public static UserDataManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new UserDataManager();

        }
        return single_instance;
    }

    public void setContext(Context context)
    {
        if(mSharedPref == null) {
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            this.loadPreferences();
        }
    }

    public void write(String key, String value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPrefString(String key) {
        return mSharedPref.getString(key, "");
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(PrefKey.PREF_UNAME, username);
        editor.putString(PrefKey.PREF_AVT_PATH, avatarPath);
        editor.putString(PrefKey.PREF_PASS,pass);
        editor.apply();
    }

    private void loadPreferences() {
        this.username = mSharedPref.getString(PrefKey.PREF_UNAME, "");
        this.avatarPath = mSharedPref.getString(PrefKey.PREF_AVT_PATH, "");
        this.pass=mSharedPref.getString(PrefKey.PREF_PASS,"");
    }

    public static void setPass(String key,String value,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPass(String key,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,null);
    }
}


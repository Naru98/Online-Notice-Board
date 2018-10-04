package com.example.sid.gpcnotifier;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sid on 10-03-2018.
 */

public class PreferenceHelper1 {

    private final String INTRO = "intro";
    private final String DEPT = "dept";
    public static final String DSIGN = "dsign";
    public static final String SFNAME = "sfname";
    public static final String SLNAME = "slname";
    private final String IDs = "id";
    private final String MOBNO = "mobno";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper1(Context context) {
        app_prefs = context.getSharedPreferences("shared1",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin1(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }

    public boolean getIsLogin1() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putfName1(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(SFNAME, loginorout);
        edit.commit();
    }

    public String getfName1() {
        return app_prefs.getString(SFNAME, "");
    }
    public void putlName1(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(SLNAME, loginorout);
        edit.commit();
    }

    public String getlName1() {
        return app_prefs.getString(SLNAME, "");
    }


    public void putHobby1(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DEPT, loginorout);
        edit.commit();
    }

    public String getHobby1() {
        return app_prefs.getString(DEPT, "");
    }
    public void putID1(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(IDs, loginorout);
        edit.commit();
    }

    public String getID1() {
        return app_prefs.getString(IDs, "");
    }
    public void putCLAS1(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DSIGN, loginorout);
        edit.commit();
    }

    public String getCLAS1() {
        return app_prefs.getString(DSIGN, "");
    }
    public void putMOBNO1(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(MOBNO, loginorout);
        edit.commit();
    }
    public String getMOBNO1() {
        return app_prefs.getString(MOBNO, "");
    }
}
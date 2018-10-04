package com.example.sid.gpcnotifier;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String ENNO = "enno";
    private final String DEPT = "dept";
    private final String ID = "id";
    private final String CLAS = "clas";
    private final String MOBNO = "mobno";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }

    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ENNO, loginorout);
        edit.commit();
    }

    public String getName() {
        return app_prefs.getString(ENNO, "");
    }

    public void putHobby(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DEPT, loginorout);
        edit.commit();
    }

    public String getHobby() {
        return app_prefs.getString(DEPT, "");
    }
    public void putID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ID, loginorout);
        edit.commit();
    }

    public String getID() {
        return app_prefs.getString(ID, "");
    }
    public void putCLAS(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CLAS, loginorout);
        edit.commit();
    }

    public String getCLAS() {
        return app_prefs.getString(CLAS, "");
    }
    public void putMOBNO(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(MOBNO, loginorout);
        edit.commit();
    }

    public String getMOBNO() {
        return app_prefs.getString(MOBNO, "");
    }
}



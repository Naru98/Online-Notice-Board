package com.example.sid.gpcnotifier;
import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseContent {

    private final String KEY_SUCCESS = "status";
    private final String KEY_MSG = "message";
    private final String KEY_AddressList = "addressList";
    private final String KEY_DATA = "Data";
    private  ArrayList<HashMap<String, String>> hashMap;
    private Activity activity;
    PreferenceHelper preferenceHelper;
    PreferenceHelper1 preferenceHelper1;

    ArrayList<HashMap<String, String>> arraylist;

    public ParseContent(Activity activity) {
        this.activity = activity;
        preferenceHelper = new PreferenceHelper(activity);
        preferenceHelper1 = new PreferenceHelper1(activity);

    }

    public boolean isSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString(KEY_SUCCESS).equals("true")) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(KEY_MSG);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    public void saveInfo(String response) {
        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putID(dataobj.getString(AndyConstants.Params.IDs));
                    preferenceHelper.putName(dataobj.getString(AndyConstants.Params.ENNO));
                    preferenceHelper.putHobby(dataobj.getString(AndyConstants.Params.DEPT));
                    preferenceHelper.putCLAS(dataobj.getString(AndyConstants.Params.CLAS));
                    preferenceHelper.putMOBNO(dataobj.getString(AndyConstants.Params.MOBNO));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void saveInfo1(String response) {
        preferenceHelper1.putIsLogin1(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper1.putID1(dataobj.getString(AndyConstants.Params.IDs));
                    preferenceHelper1.putfName1(dataobj.getString(AndyConstants.Params.SFNAME));
                    preferenceHelper1.putlName1(dataobj.getString(AndyConstants.Params.SLNAME));
                    preferenceHelper1.putCLAS1(dataobj.getString(AndyConstants.Params.DSIGN));
                    preferenceHelper1.putHobby1(dataobj.getString(AndyConstants.Params.DEPT));
                    preferenceHelper1.putMOBNO1(dataobj.getString(AndyConstants.Params.MOBNO));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
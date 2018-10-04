package com.example.sid.gpcnotifier;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class Addnot extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnlogin;
    private RadioButton b1, b2, b3;
    private ParseContent parseContent;
    private  PreferenceHelper1 preferenceHelper1;
    private final int LoginTask = 1;
    private String a = "a";
    private String p = "p";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        parseContent = new ParseContent(this);
        preferenceHelper1 = new PreferenceHelper1(this);
        etusername = (EditText) findViewById(R.id.nno1);
        etpassword = (EditText) findViewById(R.id.nno2);
        b1 = findViewById(R.id.nno34);
        b2 = findViewById(R.id.nno45);
        b3 = findViewById(R.id.nno54);
        btnlogin = findViewById(R.id.nno64);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login() throws IOException, JSONException {

        if (!AndyUtils.isNetworkAvailable(Addnot.this)) {
            Toast.makeText(Addnot.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(Addnot.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(AndyConstants.Params.TIT, etusername.getText().toString());
        map.put(AndyConstants.Params.SUB, etpassword.getText().toString());
        map.put(AndyConstants.Params.BY, preferenceHelper1.getCLAS1());
        if (b1.isChecked())
        {
            map.put(AndyConstants.Params.TO, b1.getText().toString());
        }else {
            if (b2.isChecked()){
                map.put(AndyConstants.Params.TO, b2.getText().toString());
            }else
            {
                if (b3.isChecked()){
                    map.put(AndyConstants.Params.TO, b3.getText().toString());
                }
            }
        }
        if (preferenceHelper1.getCLAS1().equals("Principal"))
        {
            map.put(AndyConstants.Params.STAT, a);
        }else
        {
            map.put(AndyConstants.Params.STAT, p);
        }
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.ADDNOTE);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss", result);
                onTaskCompleted(result,LoginTask);
            }
        }.execute();
    }

    private void onTaskCompleted(String response,int task) {
        Log.d("responsejson", response.toString());
        AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
        switch (task) {
            case LoginTask:

                if (parseContent.isSuccess(response)) {
                    Toast.makeText(Addnot.this, "Notice Posted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Addnot.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();

                }else {
                    Toast.makeText(Addnot.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
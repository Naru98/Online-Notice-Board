package com.example.sid.gpcnotifier;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Signupl extends AppCompatActivity {

    private EditText etname, etlname, etusername, etpassword;
    private RadioButton ba1, ba2, ba34;
    private RadioButton bb1, bb2, bb34, bb4;
    private Button btnregister;
    private TextView tvlogin;
    private ParseContent parseContent;
    private PreferenceHelper1 preferenceHelper1;
    private final int RegTask = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupl);

        preferenceHelper1 = new PreferenceHelper1(this);
        parseContent = new ParseContent(this);

        etname = (EditText) findViewById(R.id.ma1);
        etlname = (EditText) findViewById(R.id.ma2);
        ba1 = findViewById(R.id.ma3);
        ba2 = findViewById(R.id.ma4);
        ba34 = findViewById(R.id.ma5);
        bb1 = findViewById(R.id.ma6);
        bb2 = findViewById(R.id.ma7);
        bb34 = findViewById(R.id.ma8);
        bb4 = findViewById(R.id.ma9);
        etusername = (EditText) findViewById(R.id.ma10);
        etpassword = (EditText) findViewById(R.id.ma11);

        btnregister = (Button) findViewById(R.id.ma12);
        tvlogin = (TextView) findViewById(R.id.ma13);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signupl.this,Loginl.class);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    register();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void register() throws IOException, JSONException {
        if (!AndyUtils.isNetworkAvailable(Signupl.this)) {
            Toast.makeText(Signupl.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(Signupl.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(AndyConstants.Params.SFNAME, etname.getText().toString());
        map.put(AndyConstants.Params.SLNAME, etlname.getText().toString());
        if (ba1.isChecked()) {
            map.put(AndyConstants.Params.DSIGN, ba1.getText().toString());
        }else {
            if (ba2.isChecked()){
                map.put(AndyConstants.Params.DSIGN, ba2.getText().toString());
            }else {
                if (ba34.isChecked()){
                    map.put(AndyConstants.Params.DSIGN, ba34.getText().toString());
                }
            }
        }
        if (bb1.isChecked()) {
            map.put(AndyConstants.Params.DEPT, bb1.getText().toString());
        }else {
            if (bb2.isChecked()) {
                map.put(AndyConstants.Params.DEPT, bb2.getText().toString());
            } else {
                if (bb34.isChecked()) {
                    map.put(AndyConstants.Params.DEPT, bb34.getText().toString());
                } else {
                    if (bb4.isChecked()) {
                        map.put(AndyConstants.Params.DEPT, bb4.getText().toString());
                    }
                }
            }
        }
        map.put(AndyConstants.Params.MOBNO, etusername.getText().toString());
        map.put(AndyConstants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.REGISTERS);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss", result);
                onTaskCompleted(result, RegTask);
            }
        }.execute();
    }
    private void onTaskCompleted(String response,int task) {
        Log.d("responsejson", response.toString());
        AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
        switch (task) {
            case RegTask:

                if (parseContent.isSuccess(response)) {
                    parseContent.saveInfo1(response);
                    Toast.makeText(Signupl.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signupl.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else {
                    Toast.makeText(Signupl.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
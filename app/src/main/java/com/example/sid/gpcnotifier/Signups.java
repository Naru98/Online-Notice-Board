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

public class Signups extends AppCompatActivity {

    private EditText etname, etusername, etpassword;
    private RadioButton ba1, ba2, ba34;
    private RadioButton bb1, bb2, bb34;
    private Button btnregister;
    private TextView tvlogin;
    private ParseContent parseContent;
    private PreferenceHelper preferenceHelper;
    private final int RegTask = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signups);

        preferenceHelper = new PreferenceHelper(this);
        parseContent = new ParseContent(this);

        etname = (EditText) findViewById(R.id.snr1);
        ba1 = findViewById(R.id.snr2);
        ba2 = findViewById(R.id.snr3);
        ba34 = findViewById(R.id.snr4);
        bb1 = findViewById(R.id.snr5);
        bb2 = findViewById(R.id.snr6);
        bb34 = findViewById(R.id.snr7);
        etusername = (EditText) findViewById(R.id.snr8);
        etpassword = (EditText) findViewById(R.id.snr9);

        btnregister = (Button) findViewById(R.id.snr10);
        tvlogin = (TextView) findViewById(R.id.snr11);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signups.this,Logins.class);
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
        if (!AndyUtils.isNetworkAvailable(Signups.this)) {
            Toast.makeText(Signups.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(Signups.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(AndyConstants.Params.ENNO, etname.getText().toString());
        if (ba1.isChecked()) {
            map.put(AndyConstants.Params.DEPT, ba1.getText().toString());
        }else {
            if (ba2.isChecked()){
                map.put(AndyConstants.Params.DEPT, ba2.getText().toString());
            }else {
                if (ba34.isChecked()){
                    map.put(AndyConstants.Params.DEPT, ba34.getText().toString());
                }
            }
        }
        if (bb1.isChecked()) {
            map.put(AndyConstants.Params.CLAS, bb1.getText().toString());
        }else {
            if (bb2.isChecked()){
                map.put(AndyConstants.Params.CLAS, bb2.getText().toString());
            }else {
                if (bb34.isChecked()){
                    map.put(AndyConstants.Params.CLAS, bb34.getText().toString());
                }
            }
        }
        map.put(AndyConstants.Params.MOBNO, etusername.getText().toString());
        map.put(AndyConstants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.REGISTER);
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
                    parseContent.saveInfo(response);
                    Toast.makeText(Signups.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signups.this,Main2Activitystud.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else {
                    Toast.makeText(Signups.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
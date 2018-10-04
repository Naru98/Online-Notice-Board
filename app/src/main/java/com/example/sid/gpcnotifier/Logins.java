package com.example.sid.gpcnotifier;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;

public class Logins extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnlogin;
    private TextView tvreg;
    private ParseContent parseContent;
    private final int LoginTask = 1;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);

        parseContent = new ParseContent(this);
        preferenceHelper = new PreferenceHelper(this);

        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);

        btnlogin = (Button) findViewById(R.id.btn);
        tvreg = (TextView) findViewById(R.id.tvreg);

        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logins.this, Signups.class);
                startActivity(intent);
            }
        });

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

        if (!AndyUtils.isNetworkAvailable(Logins.this)) {
            Toast.makeText(Logins.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(Logins.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(AndyConstants.Params.ENNO, etusername.getText().toString());
        map.put(AndyConstants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.LOGIN);
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

                    parseContent.saveInfo(response);
                    Toast.makeText(Logins.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Logins.this,Main2Activitystud.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();

                }else {
                    Toast.makeText(Logins.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
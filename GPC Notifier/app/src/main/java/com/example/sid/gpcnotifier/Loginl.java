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

public class Loginl extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnlogin;
    private TextView tvreg;
    private ParseContent parseContent;
    private final int LoginTask = 1;
    private PreferenceHelper1 preferenceHelper1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginl);

        parseContent = new ParseContent(this);
        preferenceHelper1 = new PreferenceHelper1(this);

        etusername = (EditText) findViewById(R.id.etusername1);
        etpassword = (EditText) findViewById(R.id.etpassword1);

        btnlogin = (Button) findViewById(R.id.btn1);
        tvreg = (TextView) findViewById(R.id.tvreg1);

        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loginl.this, Signups.class);
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

        if (!AndyUtils.isNetworkAvailable(Loginl.this)) {
            Toast.makeText(Loginl.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(Loginl.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(AndyConstants.Params.MOBNO, etusername.getText().toString());
        map.put(AndyConstants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.LOGINS);
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

                    parseContent.saveInfo1(response);
                    Toast.makeText(Loginl.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Loginl.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();

                }else {
                    Toast.makeText(Loginl.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
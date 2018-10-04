package com.example.sid.gpcnotifier;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    private PreferenceHelper preferenceHelper;
    private PreferenceHelper1 preferenceHelper1;
    FloatingActionButton fb1;
    ListView StudentListView;
    ProgressBar progressBar;
    String HttpUrl = "https://rawoolnar98.000webhostapp.com/Student/AllStudentData.php";
    List<String> IdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        preferenceHelper = new PreferenceHelper(this);
        preferenceHelper1 = new PreferenceHelper1(this);
        if (preferenceHelper.getIsLogin()) {
            Intent intent = new Intent(MainActivity.this, Main2Activitystud.class);
            startActivity(intent);
            this.finish();
        } else {
            if (preferenceHelper1.getIsLogin1()) {
                if (preferenceHelper1.getCLAS1().equals("Lecturer") || preferenceHelper1.getCLAS1().equals("None")) {
                    startActivity(new Intent(MainActivity.this,Main2Activitylect.class));
                    this.finish();
                }else {
                    int i = 1;
                }

            } else {
                Intent intent = new Intent(MainActivity.this, Welcome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        fb1 = findViewById(R.id.floatb1);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Addnot.class));

            }
        });

        StudentListView = (ListView)findViewById(R.id.listview1);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        new GetHttpResponse(MainActivity.this).execute();

        //Adding ListView Item click Listener.
        StudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(MainActivity.this,ShowSingleRecordActivity.class);

                // Sending ListView clicked value using intent.
                intent.putExtra("ListViewValue", IdList.get(position).toString());

                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mmynote1:
                startActivity(new Intent(MainActivity.this,Mynote.class));
                break;

            case R.id.msett1:
                startActivity(new Intent(MainActivity.this,Setting.class));
                break;

            case R.id.mlog1:
                preferenceHelper1.putIsLogin1(false);
                Intent intent = new Intent(MainActivity.this,Welcome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                MainActivity.this.finish();

                break;
            case R.id.mex1:
                finish();
                break;
            case R.id.renotice:
                startActivity(new Intent(MainActivity.this,Reqnote.class));
                break;

        }
        return true;
    }


    // JSON parse class started from here.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Student> studentList;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Passing HTTP URL to HttpServicesClass Class.
            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            Student student;

                            studentList = new ArrayList<Student>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                student = new Student();

                                jsonObject = jsonArray.getJSONObject(i);

                                // Adding Student Id TO IdList Array.
                                IdList.add(jsonObject.getString("id").toString());

                                //Adding Student Name.
                                student.StudentName = jsonObject.getString("name").toString();

                                student.StudentName1 = jsonObject.getString("phone_number").toString();

                                studentList.add(student);

                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.GONE);

            StudentListView.setVisibility(View.VISIBLE);
            fb1.setVisibility(View.VISIBLE);

            ListAdapterClass adapter = new ListAdapterClass(studentList, context);

            StudentListView.setAdapter(adapter);

        }
    }
}

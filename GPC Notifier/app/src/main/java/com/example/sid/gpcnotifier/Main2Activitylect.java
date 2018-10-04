package com.example.sid.gpcnotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Main2Activitylect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_activitylect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
    }
}

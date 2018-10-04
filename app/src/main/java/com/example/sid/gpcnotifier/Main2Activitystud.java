package com.example.sid.gpcnotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Main2Activitystud extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_activitystud);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
    }
}

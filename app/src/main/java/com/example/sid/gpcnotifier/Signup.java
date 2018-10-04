package com.example.sid.gpcnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button lect2 = (Button) findViewById(R.id.lect2);
        Button stud2 = (Button) findViewById(R.id.stud2);
        lect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5 = new Intent(Signup.this,Signupl.class);
                startActivity(i5);
            }
        });
        stud2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6 = new Intent(Signup.this,Signups.class);
                startActivity(i6);
            }
        });
    }
}

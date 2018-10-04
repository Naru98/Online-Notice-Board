package com.example.sid.gpcnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button lect = (Button) findViewById(R.id.lect1);
        Button stud = (Button) findViewById(R.id.stud1);
        lect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Login.this,Loginl.class);
                startActivity(i3);
            }
        });
        stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(Login.this,Logins.class);
                startActivity(i4);
            }
        });
    }
}

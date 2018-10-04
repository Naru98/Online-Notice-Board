package com.example.sid.gpcnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button sgin = (Button) findViewById(R.id.login1);
        Button signup = (Button) findViewById(R.id.signup1);
        sgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Welcome.this,Login.class);
                startActivity(i1);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Welcome.this,Signup.class);
                startActivity(i2);
            }
        });
    }
}

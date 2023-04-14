package com.example.safeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mWelcome,mWelcomeMsg;
    Button elderbtn,childbtn,womanbtn,specialbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWelcome=findViewById(R.id.welcome);
        mWelcomeMsg=findViewById(R.id.welcomeMsg);
        elderbtn=findViewById(R.id.elderCitizen);
        childbtn=findViewById(R.id.child);
        womanbtn=findViewById(R.id.woman);
        specialbtn=findViewById(R.id.specialNeed);

        elderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),help.class));
            }
        });

        childbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),help.class));
            }
        });

        womanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),help.class));
            }
        });

        specialbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),help.class));
            }
        });
    }
}
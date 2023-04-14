package com.example.safeme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class HomePage extends AppCompatActivity {
    Button madC, maP, mcM, mmT, md;
    DataBaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        madC = findViewById(R.id.addContacts);
        maP = findViewById(R.id.selectAlertPreference);
        mcM=findViewById(R.id.selectClammingMusic);
        mmT = findViewById(R.id.setMedicineTime);
        md = findViewById(R.id.done);

        myDB = new DataBaseHandler(this);

        madC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContactList.class));
            }
        });
        md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), help.class));
            }
        });

        mcM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
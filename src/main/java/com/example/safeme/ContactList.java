package com.example.safeme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    Button b1,b2,mdone;
    EditText e1;
    ListView listView;
    SQLiteOpenHelper s1;
    SQLiteDatabase sqLiteDatabase;
    DataBaseHandler myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        e1=findViewById(R.id.phone);
        b1=findViewById(R.id.add);
        b2=findViewById(R.id.delete);
        mdone=findViewById(R.id.done);

        myDB=new DataBaseHandler(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sr=e1.getText().toString();
                addData(sr);
                Toast.makeText(ContactList.this, "Data added", Toast.LENGTH_SHORT).show();
                e1.setText("");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDatabase=myDB.getWritableDatabase();
                String x=e1.getText().toString();
                deleteData(x);
                Toast.makeText(ContactList.this, "Data deleted", Toast.LENGTH_SHORT).show();
            }
        });

        mdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),help.class));
            }
        });

    }
    private void addData(String sr){
        boolean insertData = myDB.addData( sr);
    
        if(insertData=true){
            Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Attempt is unsuccessful ", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean deleteData(String x){
        return sqLiteDatabase.delete(DataBaseHandler.TABLE_NAME, DataBaseHandler.col2 + "=?", new String[]{x})>0;
    }
    private void loadData(){
        ArrayList<String> theList=new ArrayList<>();
        Cursor data=myDB.getListContents();
        if (data.getCount()==0){
            Toast.makeText(this, "there is no content", Toast.LENGTH_SHORT).show();
        }
        else {
            while(data.moveToNext()){
               theList.add(data.getString(1)) ;
                ListAdapter listAdapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
    }
}
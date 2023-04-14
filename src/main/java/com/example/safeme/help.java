package com.example.safeme;

import static android.Manifest.permission.CALL_PHONE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class help extends AppCompatActivity {

    Button mlogOut,msettings;
    ImageButton mhelp;
    DataBaseHandler myDB;
    private static final int REQUEST_LOCATION=1;

    LocationManager locationManager;
    String x="",y="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mhelp=findViewById(R.id.help);
        mlogOut=findViewById(R.id.logOut);
        msettings=findViewById(R.id.Settings);
        final MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.music);
        myDB=new DataBaseHandler(this);

        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        }
        else{
            startTrack();
        }
        msettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),ContactList.class));
             }
        });

        mlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),logIn.class));
                finish();
            }
        });

        mhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Toast.makeText(help.this, "PLEASE DON'T PANIC " +"\n"+
                        "WE HAVE ALREADY INFORMED YOUR GUARDIAN " +"\n"+
                        "HELP WILL COME SOON ", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
    }

    private void startTrack() {
        if (ActivityCompat.checkSelfPermission(help.this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(help.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }

        else{
            Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                double lat=location.getLatitude();
                double lon=location.getLongitude();
                x=String.valueOf(lat);
                y=String.valueOf(lon);
            }
            else{
                Toast.makeText(this,"UNable to find location",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onGPS() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                //dialogInterface.cancel();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              //  dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void loadData() {
        ArrayList<String> theList=new ArrayList<>( );
        Cursor data=myDB.getListContents();
        if(data.getCount()==0){
            Toast.makeText(this, "no contact in the list to show", Toast.LENGTH_SHORT).show();
        }
        else{
            String msg="I NEED HELP LATITUDE:"+x+"LONGITUDE:"+y;
            String number="";
            while (data.moveToNext()){
                theList.add(data.getString(1));
                number=number+data.getString(1)+(data.isLast()?"":";");
                call();
            }
            if(!theList.isEmpty()){
                sendSms(number,msg,true);
            }
        }
    }

    private void sendSms(String number, String msg, boolean b) {
        Intent smsIntent=new Intent(android.content.Intent.ACTION_SENDTO);
        Uri.parse("smsto:"+number);
        smsIntent.putExtra("smsbody",msg);
        startActivity(smsIntent);
    }

    private void call() {
        Intent i=new Intent(android.content.Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:999"));
        if(ContextCompat.checkSelfPermission( getApplicationContext(),CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
            startActivity(i);
        }
        else{
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.N){
                requestPermissions(new String[]{CALL_PHONE},1);
            }
        }

    }
}
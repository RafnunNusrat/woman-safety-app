package com.example.safeme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myList.db";
    public static final String TABLE_NAME = "myList_data";
    public static final String Col1="ID";
    public static final String col2="ITEM";
    public DataBaseHandler(Context context ){super(context,DATABASE_NAME,null,1);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable="CREATE TABLE " +TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+" ITEM TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String a="DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(a);
        onCreate(sqLiteDatabase);

    }
    public boolean addData (String item){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues( );
        contentValues.put(col2,item);
        long result=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }

    public Cursor getListContents(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT* FROM " +TABLE_NAME,null);
    }
}

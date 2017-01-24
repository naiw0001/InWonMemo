package com.example.inwon.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by inwon on 2017-01-18.
 */

public class Dbhelper extends SQLiteOpenHelper{


    public Dbhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memo (title TEXT, content TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE memo");
        onCreate(db);
    }
    public void insert(String query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
    public void delete(String query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
    public String select(String title){
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("select * from memo where title = '"+title+"'",null);
        while(cursor.moveToNext()){
            result += "제목 : "
                    +cursor.getString(0)
                    +", 내용 : "
                    +cursor.getString(1)
                    +"\n";
        }
        return result;
    }
    public String select(){
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("select * from memo",null);
        while(cursor.moveToNext()){
            result += "제목 : "
                    +cursor.getString(0)
                    +", 내용 : "
                    +cursor.getString(1)
                    +"\n";
        }
        return result;
    }
    public String select_title(String title){
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("select * from memo where title = '"+title+"'",null);
        while(cursor.moveToNext()){
            result += "제목 : "
                    +cursor.getString(0);
        }
        return result;
    }
    public String[] select_title(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from memo",null);
        String[] result = new String[cursor.getCount()];
        int i = 0;
        while(cursor.moveToNext()){
            result[i] = cursor.getString(0);
            i++;
        }
        return result;
    }

    public int row_count(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from memo",null);
        int i = cursor.getCount();
        return i;
    }

    public String select_content(String title){
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("select * from memo where title = '"+title+"'",null);
        while(cursor.moveToNext()){
            result += "내용 : "
                    +cursor.getString(1);
        }
        return result;
    }
    public String select_content(){
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("select * from memo",null);
        while(cursor.moveToNext()){
            result += "내용 : "
                    +cursor.getString(1)
                    +"\n";
        }
        return result;
    }

}

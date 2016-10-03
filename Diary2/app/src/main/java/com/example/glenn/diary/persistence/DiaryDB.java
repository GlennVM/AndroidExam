package com.example.glenn.diary.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Glenn on 18/08/2016.
 */
public class DiaryDB {
    private SQLiteDatabase db;
    private DiaryDBHelper dbHelper;

    public DiaryDB(Context context){
        dbHelper = new DiaryDBHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
        if(db == null){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db.close();
    }

    public Cursor getDiaries(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }

    public long insertDiary(String table,String entry, ContentValues values){
        return  db.insert(table,entry,values);

    }

    public void deleteDiary(String table, String whereClause, String[] whereArgs){
        db.delete(table,whereClause,whereArgs);
    }
}

package com.example.glenn.reason.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Glenn on 18/08/2016.
 */
public class ReasonDB {
    private SQLiteDatabase db;
    private ReasonDBHelper dbHelper;

    public ReasonDB(Context context){
        dbHelper = new ReasonDBHelper(context);
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

    public Cursor getReasons(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }

    public long insertReason(String table,String entry, ContentValues values){
        return  db.insert(table,entry,values);

    }

    public void deleteReason(String table, String whereClause, String[] whereArgs){
        db.delete(table,whereClause,whereArgs);
    }
}
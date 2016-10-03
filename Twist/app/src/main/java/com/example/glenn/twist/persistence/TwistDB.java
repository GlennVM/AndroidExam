package com.example.glenn.twist.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Glenn on 19/08/2016.
 */
public class TwistDB {
    private SQLiteDatabase db;
    private TwistDBHelper dbHelper;

    public TwistDB(Context context) {dbHelper = new TwistDBHelper(context);}

    public void open(){
        db = dbHelper.getWritableDatabase();
        if(db == null){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db.close();
    }

    public Cursor getTwists(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }

    public long insertTwist(String table,String entry, ContentValues values){
        return  db.insert(table,entry,values);
    }

    public void updateTwist(String table, ContentValues values, String whereClause, String[] whereArgs){
        db.update(table, values, whereClause, whereArgs);
    }

    public void deleteTwist(String table, String whereClause, String[] whereArgs){
        db.delete(table,whereClause,whereArgs);
    }
}

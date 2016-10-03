package com.example.glenn.reddit.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Glenn on 23/08/2016.
 */
public class RedditDB {
    private SQLiteDatabase db;
    private RedditDBHelper dbHelper;

    public RedditDB(Context context){
        dbHelper = new RedditDBHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
        if(db == null){
            dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db.close();
    }

    public Cursor getSubreddits(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }

    public long insertSubreddit(String table, String entry, ContentValues values){
        return db.insert(table, entry, values);
    }

    public long updateSubreddit(String table, ContentValues values, String whereClause, String[] whereArgs){
        return db.update(table,values,whereClause,whereArgs);
    }

    public long deleteSubreddit(String table, String whereClause, String[] whereArgs){
        return db.delete(table, whereClause, whereArgs);
    }
}

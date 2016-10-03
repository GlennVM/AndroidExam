package com.example.glenn.reddit.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Glenn on 23/08/2016.
 */
public class RedditDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Reddit.db";
    private static final int DATABASE_VERSION = 2;

    public RedditDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.SQL_CREATE_SUBREDDITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.SQL_DELETE_SUBREDDITS);
        onCreate(db);
    }
}

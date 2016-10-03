package com.example.glenn.reddit.persistence;

import android.provider.BaseColumns;

/**
 * Created by Glenn on 23/08/2016.
 */
public class Constants {
    public static final String SQL_CREATE_SUBREDDITS = "CREATE TABLE "+PostEntry.TABLE_NAME+" ("+
            PostEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            PostEntry.COLUMN_NAME_POST_TITLE+ " TEXT NOT NULL,"+
            PostEntry.COLUMN_NAME_POST_SUBREDDIT +" TEXT NOT NULL,"+
            PostEntry.COLUMN_NAME_POST_UPVOTES+ " INTEGER)";

    public static final String SQL_DELETE_SUBREDDITS = "DROP TABLE IF EXISTS "+PostEntry.TABLE_NAME;

    public static abstract class PostEntry implements BaseColumns {
        public static final String TABLE_NAME="posts";
        public static final String COLUMN_NAME_POST_TITLE = "title";
        public static final String COLUMN_NAME_POST_UPVOTES = "upvotes";
        public static final String COLUMN_NAME_POST_SUBREDDIT = "subreddit";
        public static final String TABLE_CONTENT ="subreddit";
    }

}

package com.example.glenn.reason.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Glenn on 18/08/2016.
 */
public class Constants {
    public static final String SQL_CREATE_REASON = "CREATE TABLE "+ TwistEntry.TABLE_NAME+" ("+
            TwistEntry._ID+" integer primary key autoincrement,"+
            TwistEntry.COLUMN_NAME_TWIST_TITLE+" text not null,"+
            TwistEntry.COLUMN_NAME_TWIST_REWARD+" text not null,"+
            TwistEntry.COLUMN_NAME_TWISTEE+" text not null)";

    public static final String SQL_DELETE_REASON = "DROP TABLE IF EXISTS "+TwistEntry.TABLE_NAME;
    public static final String AUTHORITY = "com.example.glenn.reason";


    public static abstract class TwistEntry implements BaseColumns {
        public static final String TABLE_NAME = "reason";
        public static final String COLUMN_NAME_TWIST_TITLE = "title";
        public static final String COLUMN_NAME_TWIST_REWARD = "reward";
        public static final String COLUMN_NAME_TWISTEE = "twistee";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/entries");
    }
}


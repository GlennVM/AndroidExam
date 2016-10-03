package com.example.glenn.twist.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Glenn on 19/08/2016.
 */
public class Constants {

    public static final String SQL_CREATE_TWISTS = "CREATE TABLE "+TwistEntry.TABLE_NAME+" ("+
            TwistEntry._ID+" integer primary key autoincrement,"+
            TwistEntry.COLUMN_NAME_TWIST_TITLE+" text not null,"+
            TwistEntry.COLUMN_NAME_TWIST_TWISTEE+" text not null,"+
            TwistEntry.COLUMN_NAME_TWIST_MYANSWER+" text not null,"+
            TwistEntry.COLUMN_NAME_TWIST_TWISTEEANSWER+" text not null,"+
            TwistEntry.COLUMN_NAME_TWIST_REWARD+" text not null,"+
            TwistEntry.COLUMN_NAME_TWIST_WON+" INTEGER)";

    public static final String SQL_DELETE_TWISTS = "DROP TABLE IF EXISTS "+TwistEntry.TABLE_NAME;
    public static final String AUTHORITY = "com.example.glenn.twist";


    public static abstract class TwistEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_NAME_TWIST_TITLE = "title";
        public static final String COLUMN_NAME_TWIST_TWISTEE = "twistee";
        public static final String COLUMN_NAME_TWIST_MYANSWER = "myAnswer";
        public static final String COLUMN_NAME_TWIST_TWISTEEANSWER = "twisteeAnswer";
        public static final String COLUMN_NAME_TWIST_REWARD = "reward";
        public static final String COLUMN_NAME_TWIST_WON = "won";
        public static final String TABLE_CONTENT ="entry";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/entries");

    }

}

package com.example.glenn.diary.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Glenn on 18/08/2016.
 */
public class Constants {

    public static final String SQL_CREATE_DIARIES = "CREATE TABLE "+DiaryEntry.TABLE_NAME+" ("+
            DiaryEntry._ID+" integer primary key autoincrement,"+
            DiaryEntry.COLUMN_NAME_DIARY_TITLE+" text not null,"+
            DiaryEntry.COLUMN_NAME_DIARY_CONTENT+" text not null,"+
            DiaryEntry.COLUMN_NAME_DIARY_DATE+" long)";

    public static final String SQL_DELETE_DIARIES = "DROP TABLE IF EXISTS "+DiaryEntry.TABLE_NAME;
    public static final String AUTHORITY = "glenn.diary";


    public static abstract class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_NAME_DIARY_TITLE = "title";
        public static final String COLUMN_NAME_DIARY_CONTENT = "content";
        public static final String COLUMN_NAME_DIARY_DATE = "recordDate";
        public static final String TABLE_CONTENT ="entry";
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/entries");
    }
}

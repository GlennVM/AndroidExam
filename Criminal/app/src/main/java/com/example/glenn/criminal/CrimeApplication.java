package com.example.glenn.criminal;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.glenn.criminal.model.DaoMaster;
import com.example.glenn.criminal.model.DaoSession;

/**
 * Created by Glenn on 25/08/2016.
 */
public class CrimeApplication extends Application {

    public DaoSession session;

    @Override
    public void onCreate(){
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"crime-db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        session = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return session;
    }
}

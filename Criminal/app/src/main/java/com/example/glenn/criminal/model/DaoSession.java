package com.example.glenn.criminal.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig crimeDaoConfig;

    private final CrimeDao crimeDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        crimeDaoConfig = daoConfigMap.get(CrimeDao.class).clone();
        crimeDaoConfig.initIdentityScope(type);

        crimeDao = new CrimeDao(crimeDaoConfig, this);

        registerDao(Crime.class, crimeDao);
    }
    
    public void clear() {
        crimeDaoConfig.getIdentityScope().clear();
    }

    public CrimeDao getCrimeDao() {
        return crimeDao;
    }

    public File getPhotoFile(Context context, Crime crime){

        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return new File(dir,crime.getPhotoFileName());
    }

}

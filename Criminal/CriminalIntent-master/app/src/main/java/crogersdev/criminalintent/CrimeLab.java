package crogersdev.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by chris on 2/20/16.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();

        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID crimeID) {
        for (Crime c : mCrimes) {
            //Log.d("CrimeLabSrc", "checking if " + c.getId() + " == " + crimeID);
            // SO VERY TRICKY:
            // == operator on two UUIDs compares to see if the two objects are equal,
            // not their values.
            // please use .equals() for comparing values.
            // Duh...  Why *would* you assume == compares values?  </sarcasm>
            if (c.getId().equals(crimeID)) {
                //Log.d("CrimeLabSrc", "it did!!!");
                return c;
            }
        }
        Log.w("CrimeLabSrc", "Unable to find " + crimeID + " .");
        return null;
    }

    // get method forces singleton
    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }
}

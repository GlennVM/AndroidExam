package crogersdev.criminalintent;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by chris on 1/31/16.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDate;

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    private boolean mSolved;


    public Crime() {
        mId = UUID.randomUUID();
        //Log.d("CrimeSrc", "UUID was: " + mId);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
        mDate = dateFormat.format(date);
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}

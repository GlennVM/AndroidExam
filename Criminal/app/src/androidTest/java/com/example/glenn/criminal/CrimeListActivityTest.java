package com.example.glenn.criminal;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Glenn on 26/08/2016.
 */
public class CrimeListActivityTest extends ActivityInstrumentationTestCase2<CrimeListActivity> {

    private CrimeListActivity mCrimeListActivity;

    public CrimeListActivityTest() {
        super(CrimeListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCrimeListActivity = getActivity();
    }

    public void testPreconditions() {
        assertEquals("Activity is not an instance of CrimeListActivity", CrimeListActivity.class, getActivity()
                .getClass());
    }

    public void testActivityTitle() {
        final String expected = mCrimeListActivity.getString(R.string.app_name);
        final String actual = mCrimeListActivity.getTitle().toString();
        assertEquals(expected, actual);
    }
}

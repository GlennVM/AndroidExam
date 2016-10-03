package crogersdev.criminalintent;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by chris on 2/27/16.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}

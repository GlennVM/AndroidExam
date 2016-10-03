package com.example.glenn.reason;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.glenn.reason.persistence.Constants;
import com.example.glenn.reason.persistence.ReasonDB;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 16/08/2016.
 */
public class ReasonDetailFragment extends Fragment{
    @Bind(R.id.txtTwistTitle)
    TextView title;

    @Bind(R.id.txtTwistee)
    TextView twistee;

    @Bind(R.id.txtReward)
    TextView reward;

    @Bind(R.id.btnTwisteeRight)
    Button twisteeRight;

    @Bind(R.id.txtYourAnswer)
    TextView yourAnswer;

    @Bind(R.id.txtTwisteeAnswer)
    TextView twisteeAnswer;

    ReasonDB db;

    public static final String TWISTINDEX  = "TWISTINDEX";
    private int index;
    public ReasonDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new ReasonDB(getActivity());
        db.open();
        View layout = inflater.inflate(R.layout.fragment_reason_detail, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            int index = args.getInt(TWISTINDEX);
            this.index = index;
            updateTwistInfo(index);
        }
    }

    public void updateTwistInfo(int position){

        Log.i ("info", "" + position);
        String[] projection = {
                Constants.TwistEntry._ID,
                Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE
        };

        String sortOrder = Constants.TwistEntry._ID+ " DESC";
        Log.i("info", "Were here now!!!!!!");
        Cursor c = db.getReasons(Constants.TwistEntry.TABLE_NAME,
                projection, null, null, null, null, null, null);

        c.moveToFirst();
        while(!c.isAfterLast()) {
            Log.i("info", "ID: " + c.getInt(c.getColumnIndex(Constants.TwistEntry._ID)));
            if(c.getInt(c.getColumnIndex(Constants.TwistEntry._ID)) == position + 1) {
                Log.i("info", "ID correct");
                title.setText(c.getString(c.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE)));
                Log.i("info", "" + Constants.TwistEntry.COLUMN_NAME_TWISTEE);
                twistee.setText(c.getString(c.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWISTEE)));
                //reward.setText(c.getString(c.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD)));
            }
            c.moveToNext();
        }
        /**title.setText(Twists.twists[index]);
        twistee.setText(Twists.twistees[index]);
        reward.setText(Twists.Reward[index]);
        twisteeRight.setText(Twists.twistees[index] + " is Right");
        yourAnswer.setText(Twists.yourAnswer[index]);
        twisteeAnswer.setText(Twists.twisteeAnswer[index]);*/
    }
}

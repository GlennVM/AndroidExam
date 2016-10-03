package com.example.glenn.twist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.glenn.twist.persistence.Constants;
import com.example.glenn.twist.persistence.TwistDB;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 26/08/2016.
 */
public class ScoreboardFragment extends Fragment {

    @Bind(R.id.lstScoreboard)
    ListView lstScoreboard;

    View.OnClickListener listener;


    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        ButterKnife.bind(this, layout);

        TwistDB db = new TwistDB(getActivity());
        db.open();

        String[] projection = {
                Constants.TwistEntry._ID,
                Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE,
                Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE,
                Constants.TwistEntry.COLUMN_NAME_TWIST_MYANSWER,
                Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEEANSWER,
                Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD,
                Constants.TwistEntry.COLUMN_NAME_TWIST_WON
        };

        Cursor c = db.getTwists(Constants.TwistEntry.TABLE_NAME,
                projection, null, null, Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE, null, null, null);

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> won = new ArrayList<Integer>();
        ArrayList<Integer> lost = new ArrayList<Integer>();

        c.moveToFirst();

        while (!c.isAfterLast()){
            names.add(c.getString(c.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE)) + " Won: " + c.getString(c.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_WON)));
            c.moveToNext();
        }

        String[] values = names.toArray(new String[names.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        lstScoreboard.setAdapter(adapter);

        return layout;
    }
}

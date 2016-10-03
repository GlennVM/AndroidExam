package com.example.glenn.reason;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.glenn.reason.persistence.Constants;
import com.example.glenn.reason.persistence.ReasonDB;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 16/08/2016.
 */
public class ReasonListFragment extends Fragment {

    private ReasonDB db;
    private SimpleCursorAdapter adapter;

    @Bind(R.id.twists)
    ListView twists;

    private OnTwistSelectedListener twistSelectedListener;

    public ReasonListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new ReasonDB(getActivity());
        db.open();

        String[] projection = {
                Constants.TwistEntry._ID,
                Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE
        };

        String sortOrder = Constants.TwistEntry._ID+ " DESC";

        Cursor c = db.getReasons(Constants.TwistEntry.TABLE_NAME,
                projection, null, null, null, null, null, null);

        c.moveToFirst();
        ArrayList<String> twis = new ArrayList<String>();
        while(!c.isAfterLast()) {
            twis.add(c.getString(c.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE)));
            c.moveToNext();
        }
        String[] t = twis.toArray(new String[twis.size()]);

        View layout = inflater.inflate(R.layout.fragment_reason_list, container, false);
        ButterKnife.bind(this,layout);
        twistSelectedListener = (OnTwistSelectedListener) getActivity();
        twists.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,t));
        twists.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                twistSelectedListener.onTwistSelected(position);
            }
        });
        return layout;
    }

    public interface OnTwistSelectedListener {
        void onTwistSelected(int index);
    }
}

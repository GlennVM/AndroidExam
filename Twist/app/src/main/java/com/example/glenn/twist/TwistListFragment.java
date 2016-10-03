package com.example.glenn.twist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.volley.VolleyError;
import com.example.glenn.twist.models.Twist;
import com.example.glenn.twist.network.TwistDownloader;
import com.example.glenn.twist.persistence.Constants;
import com.example.glenn.twist.persistence.TwistDB;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Glenn on 18/08/2016.
 */
public class TwistListFragment extends ListFragment {

    private TwistDownloader downloader;
    private TwistDownloader.VolleyCallBack<List<Twist>> callBack;

    private TwistDB db;
    private SimpleCursorAdapter adapter;
    private OnItemClickedListener listener;

    @Bind(R.id.listTwists)
    ListView listTwists;

    public interface OnItemClickedListener {
        void onItemClicked(String[] args);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    public TwistListFragment(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Maakt nieuwe instantie aan van de db
        db = new TwistDB(getActivity());
        db.open();

        //Haalt instantie op van de downloader
        downloader = TwistDownloader.getInstance(getContext());

        callBack = new TwistDownloader.VolleyCallBack<List<Twist>>(){
            @Override
            public void onSuccess(List<Twist> result) {
                //Als het lukt om twists te downloaden
                String[] projection = {
                        Constants.TwistEntry._ID,
                        Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE,
                        Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE,
                        Constants.TwistEntry.COLUMN_NAME_TWIST_MYANSWER,
                        Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEEANSWER,
                        Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD,
                        Constants.TwistEntry.COLUMN_NAME_TWIST_WON
                };

                //Haalt twists op uit de db
                Cursor c = db.getTwists(Constants.TwistEntry.TABLE_NAME,
                        projection, null, null, null, null, null, null);

                boolean inDB = false;
                //Controlleert of opgehaalde gegevens al in de db zitten
                for(int i = 0; i < result.size(); i++) {
                    c.moveToFirst();
                    for (int j = 0; j < c.getCount(); j++) {
                        Log.i("info",c.getString(1));
                        if(result.get(i).getTitle().equals(c.getString(1))){
                            Log.i("info", "Gelijk bij " + i);
                            inDB = true;
                        }
                        c.moveToNext();
                    }

                    //Als de twist nog niet in de db zit, dan voegen we die toe
                    if(!inDB) {
                        ContentValues values = new ContentValues();
                        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE,result.get(i).getTitle());
                        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE,result.get(i).getTwistee());
                        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_MYANSWER,result.get(i).getMyAnswer());
                        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEEANSWER,result.get(i).getTwisteeAnswer());
                        values.put(Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD,result.get(i).getReward());

                        db.insertTwist(Constants.TwistEntry.TABLE_NAME, null, values);
                    }
                    inDB = false;
                }

                //We vragen de db op
                c = db.getTwists(Constants.TwistEntry.TABLE_NAME,
                        projection, null, null, null, null, null, null);

                adapter = new android.widget.SimpleCursorAdapter(getContext(),
                        R.layout.twist_entry,
                        c,
                        new String[] {Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE, Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE},
                        new int[]{R.id.twist_entry_title, R.id.twist_entry_twistee},
                        0);

                adapter.setViewBinder( new android.widget.SimpleCursorAdapter.ViewBinder(){

                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        return false;
                    }
                });

                setListAdapter(adapter);
            }

            @Override
            public void onError(VolleyError error) {
                Log.i("info", error.toString());
            }
        };

        downloader.getTwists(callBack, "twists/1");
    }

    @OnClick(R.id.btnAddTwist)
    public void addNew(){
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, new AddTwistFragment(), "AddTwist");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SQLiteCursor wrapper = (SQLiteCursor) l.getItemAtPosition(position);

        String title= wrapper.getString(wrapper.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_TITLE));
        String twistee = wrapper.getString(wrapper.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEE));
        String myAnswer = wrapper.getString(wrapper.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_MYANSWER));
        String twisteeAnswer = wrapper.getString(wrapper.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_TWISTEEANSWER));
        String reward = wrapper.getString(wrapper.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_REWARD));
        String won = wrapper.getString(wrapper.getColumnIndex(Constants.TwistEntry.COLUMN_NAME_TWIST_WON));

        String[] arguments = {title, twistee, myAnswer, twisteeAnswer, reward, won};

        listener.onItemClicked(arguments);
    }
}

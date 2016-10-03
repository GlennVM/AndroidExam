package com.example.glenn.twist.twistlist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Glenn on 19/08/2016.
 */
public class TwistAdapter extends BaseAdapter {
    List<TwistEntry> twistEntries;
    @Override
    public int getCount() {
        return twistEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
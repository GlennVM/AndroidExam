package com.example.glenn.twist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Glenn on 19/08/2016.
 */
public class ActionsFragment extends Fragment {
    public ActionsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate fragment
        View layout = inflater.inflate(R.layout.fragment_actions, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @OnClick(R.id.btnAddNewTwist)
    public void addTwists(){
        //Maakt nieuwe fragment aan en plaatst die op het scherm
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, new AddTwistFragment(), "addTwist");
        ft.addToBackStack(null);
        ft.commit();
    }
}

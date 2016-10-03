package com.example.glenn.reason;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ReasonListFragment.OnTwistSelectedListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container) != null){
            //phone
            if(savedInstanceState != null){
                //niets want anders kunnen we overlapping krijgen
                return;
            }

            //Aanmaken nieuwe Fragment
            ReasonListFragment fragment = new ReasonListFragment();

            //Toevoegen van fragment aan scherm en vullen met lijst van paradoxes
            fragmentManager.beginTransaction().add(R.id.container,fragment,"Twists").commit();
        }
    }

    @Override
    public void onTwistSelected(int index){
        ReasonDetailFragment fragment = new ReasonDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ReasonDetailFragment.TWISTINDEX, index);
        fragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addTwist(View view) {
        ReasonAddFragment fragment = new ReasonAddFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

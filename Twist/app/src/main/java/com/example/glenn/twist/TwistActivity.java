package com.example.glenn.twist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TwistActivity extends AppCompatActivity implements TwistListFragment.OnItemClickedListener{

    private FragmentManager fragmentManager;
    private TwistListFragment listFragment;
    private AddTwistFragment addFragment;
    private TwistDetailFragment detailFragment;
    private final String FRAGMENT_ADD = "add";
    private final String FRAGMENT_LIST = "list";
    private final String FRAGMENT_DETAIL = "detail";
    private static int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twist);
        fragmentManager = getSupportFragmentManager();

        //Haalt extras op ==> userID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = Integer.parseInt(extras.getString("userID"));
        }

        if (findViewById(R.id.container) != null) {
            //phone
            if (savedInstanceState != null) {
                //niets want anders kunnen we overlapping krijgen
                return;
            }

            //Aanmaken nieuwe Fragment
            ActionsFragment fragment = new ActionsFragment();

            //Toevoegen van fragment aan scherm en vullen met lijst van paradoxes
            fragmentManager.beginTransaction().add(R.id.container, fragment, "List").commit();
        }
    }

    public void onItemClicked(String[] args) {
        Bundle entry = new Bundle();
        entry.putString("title",args[0]);
        entry.putString("twistee",args[1]);
        entry.putString("myAnswer",args[2]);
        entry.putString("twisteeAnswer",args[3]);
        entry.putString("reward",args[4]);
        entry.putString("won", args[5]);

        //Als op item wordt geklikt maken we nieuw fragment en geven we gegevens mee.
        detailFragment = new TwistDetailFragment();
        detailFragment.setArguments(entry);

        //We plaatsen fragment op scherm
        fragmentManager.beginTransaction().replace(R.id.container, detailFragment, FRAGMENT_DETAIL).addToBackStack(null).commit();
    }

    public void getList(View view) {
        //Maakt nieuw fragment aan
        TwistListFragment fragment = new TwistListFragment();
        //voegt onclicklistener toe
        fragment.setOnItemClickedListener(this);
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "twistList").addToBackStack(null).commit();
    }

    public void scoreboard(View view) {
        //Maakt nieuw fragment voor scoreboard
        ScoreboardFragment fragment = new ScoreboardFragment();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, "scoreboard").addToBackStack(null).commit();
    }
}


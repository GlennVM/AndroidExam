package com.example.glenn.twist;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Haalt fragmentManager op
        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container) != null){
            //phone
            if(savedInstanceState != null){
                //niets want anders kunnen we overlapping krijgen
                return;
            }

            //Aanmaken nieuwe Fragment
            LoginFragment fragment = new LoginFragment();

            //Toevoegen van fragment aan scherm en vullen met lijst van paradoxes
            fragmentManager.beginTransaction().add(R.id.container,fragment,"Login").commit();
        }
    }
}

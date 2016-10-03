package com.example.glenn.fragments_paradoxes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ParadoxListFragment.OnParadoxSelectedListener{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //verkrijgen van de FragmentManager ---> Belangrijk dat juist klasse wordt geïmporteerd bij Fragments
        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container) != null){
            //phone
            if(savedInstanceState != null){
                //niets want anders kunnen we overlapping krijgen
                return;
            }

            //Aanmaken nieuwe Fragment
            ParadoxListFragment fragment = new ParadoxListFragment();

            //Toevoegen van fragment aan scherm
            //Zal er voor zorgen dat het fragment op de juiste plaats in de activity wordt toegevoegd
            fragmentManager.beginTransaction().add(R.id.container,fragment,Constants.LIST).commit();
        }
    }

    @Override
    public void onParadoxSelected(int index) {
        //Maakt nieuwe fragment aan voor de details
        ParadoxDetailFragment fragment = new ParadoxDetailFragment();
        //Maakt nieuwe bundel aan om gegevens door te geven
        Bundle args = new Bundle();
        //Steekt index van de geselecteerde paradox in aangemaakte bundle
        //Dit zal ervoor zorgen dat we in de onStart van het fragment de index kunnen ophalen
        args.putInt(ParadoxDetailFragment.PARADOXINDEX,index);
        //geeft bundle door aan fragment
        fragment.setArguments(args);

        //Start de transactie
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //Vervangt huidig getoonde fragment door nieuwe
        transaction.replace(R.id.container,fragment);
        //Voegt het huidige fragment toe aan de backstack
        // zodat we op back kunnen drukken en de fragment terug wordt geladen
        transaction.addToBackStack(null);
        //Sluit de transactie af
        transaction.commit();
    }
}

package com.example.glenn.gameofthrones;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.glenn.gameofthrones.character.CharacterAdapter;
import com.example.glenn.gameofthrones.models.GOTCharacter;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CharacterAdapter.CardClickedListener{

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Voegt juiste view toe
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Haalt fragmentManager op ==> let op bij imports
        manager = getSupportFragmentManager();
        //Voegt nieuwe Fragment toe, plaatst dit op de juiste plaats
        manager.beginTransaction().add(R.id.container,new CharacterListFragment(),"List").commit();
    }


    @Override
    public void cardClicked(GOTCharacter character) {
        //Controlleerd of de Fragmentmanager al opgehaald is
        if(manager == null){
            //Als dit niet het geval is halen we die hier op
            manager = getSupportFragmentManager();
        }

        Bundle args = new Bundle();
        //parcelable om het door te geven naar ander fragment
        args.putParcelable("character", character);

        //We maken een nieuw detailFragment aan
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        //We geven het personage door
        fragment.setArguments(args);
        //We zullen het listFragment vervagen door het details fragment
        //We voegen dit niet toe aan de backStack want we hebben een eigen methode geschreven hiervoor
        manager.beginTransaction().replace(R.id.container,fragment,"Details").commit();

        //We stellen de titel van de ActionBar in met de naam van het personage
        getSupportActionBar().setTitle(character.getName());
    }

    @Override
    public void onBackPressed(){
        //Als er op terug geklickt wordt deze functie uitgevoerd
        //We stellen de titel in de ActionBar in
        getSupportActionBar().setTitle(R.string.app_name);
        //We maken een nieuw ListFragment aan
        Fragment listFragment = new CharacterListFragment();

        //Als het detailscherm nog is ingeladen vervagen we dit door het nieuwe listFragment
        if(manager.findFragmentByTag("Details") != null){
            manager.beginTransaction().replace(R.id.container,listFragment,"List").commit();
        }else{
            //Anders voeren we de standaardimplementatie uit
            super.onBackPressed();
        }

    }
}


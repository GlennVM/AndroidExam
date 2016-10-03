package com.example.glenn.diary;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity implements DiaryAddFragment.OnSubmitListener,DiaryListFragment.OnItemClickedListener{

    private FragmentManager manager;
    private DiaryListFragment listFragment;
    private DiaryAddFragment addFragment;
    private DiaryDetailFragment detailFragment;
    private final String FRAGMENT_ADD = "add";
    private final String FRAGMENT_LIST = "list";
    private final String FRAGMENT_DETAIL = "detail";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Stelt de juiste view in
        setContentView(R.layout.activity_main);
        //Haalt de fragmentManager op als dit nog niet gebeurd was
        if(manager == null){
            manager = getFragmentManager();
        }

        //Als de savedInstanceState niet bestaat maken we nieuwe fragments aan
        if(savedInstanceState == null){
            addFragment = new DiaryAddFragment();
            listFragment = new DiaryListFragment();
            //We voegen het listFragment toe aan het huidige scherm, op de juiste plaats
            manager.beginTransaction().add(R.id.container,listFragment,FRAGMENT_LIST).commit();
        }else {
            //Anders halen we de fragments op uit de manager
            if(manager.findFragmentByTag(FRAGMENT_LIST) == null){
                //Als het er nog niet in zit maken we een nieuw aan
                listFragment = new DiaryListFragment();
            }else{
                //Anders halen we het op uit de manager
                listFragment = (DiaryListFragment) manager.findFragmentByTag(FRAGMENT_LIST);
            }
            if(manager.findFragmentByTag(FRAGMENT_ADD) == null){
                addFragment = new DiaryAddFragment();
            }else{
                addFragment = (DiaryAddFragment) manager.findFragmentByTag(FRAGMENT_ADD);
            }

        }
        //We voegen een Listeners toe aan de bijde fragments
        //Deze interfaces zijn overschreven in deze klasse
        listFragment.setOnItemClickedListener(this);
        addFragment.setOnSubmitListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //We halen de menuInflater op
        MenuInflater inflater = getMenuInflater();
        //En voegen ons menu uit de res toe aan de menu in de ActionBar
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Haalt het aangeklikte menuItemId op
        int id = item.getItemId();

        //Vergelijkt dit id met de ids van de acties en voegt het correcte fragment toe
        switch(id){
            case R.id.action_addItem:
                manager.beginTransaction().replace(R.id.container,addFragment,FRAGMENT_ADD).commit();
                break;
            case R.id.action_listItems:
                manager.beginTransaction().replace(R.id.container,listFragment,FRAGMENT_LIST).commit();
                break;
        }

        return true;
    }


    @Override
    public void onItemClicked(String[] args) {
        Log.i("onItemClicked","pls im clicking load detail");
        //We controlleren of de fragmentManager opgehaald is, anders doen we dit
        if(manager == null){
            manager = getFragmentManager();
        }

        //We maken een nieuwe bundle aan om info door te geven aan fragment
        Bundle entry = new Bundle();
        //We steken de data in de bundle
        entry.putString("title",args[0]);
        entry.putString("content", args[1]);
        entry.putString("date", args[2]);

        //We maken een nieuw fragment aan
        detailFragment = new DiaryDetailFragment();
        //We voegen deze bundle toe aan de argumenten van het fragment
        detailFragment.setArguments(entry);

        //We tonen het fragment op het scherm
        manager.beginTransaction().replace(R.id.container, detailFragment, FRAGMENT_DETAIL).commit();

    }

    @Override
    public void onEntrySubmit() {
        //Als we de entry hebben toegevoegd tonen we het listFragment terug op het scherm
        manager.beginTransaction().replace(R.id.container,listFragment,FRAGMENT_LIST).commit();
    }
}

package com.example.glenn.gameofthrones;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.glenn.gameofthrones.character.CharacterAdapter;
import com.example.glenn.gameofthrones.models.GOTCharacter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 10/08/2016.
 */
public class CharacterListFragment extends Fragment{

    //We binden de lokale variabele met de RecyclerView uit de xml
    @Bind(R.id.characterList)
    RecyclerView characterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //We maken een nieuwe FrameLayout aan
        FrameLayout layout = (FrameLayout)inflater.inflate(R.layout.fragment_character_list,container,false);
        ButterKnife.bind(this, layout);
        //We maken een nieuwe lijst waar GOTCharacters in zullen komen
        List<GOTCharacter> characters = new ArrayList<>();

        //We overlopen alle charachternamen uit de klasse Data
        for(int i = 0 ; i < Data.characterNames.length;i++)
        {
            //Voor elk personage in de lijst met namen maken we een nieuw object aan
            //We steken de bijpassende data in dit object
            //We voegen dit object toe aan onze lijst van characters
            characters.add(new GOTCharacter(Data.characterNames[i],Data.characterDetails[i],Data.characterPictures[i]));
        }

        //We maken een nieuwe LayoutManager aan, dit is verplicht bij recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //We stellen de aangemaakte layoutmanager in bij onze RecyclerView
        characterList.setLayoutManager(layoutManager);

        //We maken een nieuwe CharacterAdapter aan, dit is verplicht bij een recyclerView
        CharacterAdapter adapter = new CharacterAdapter(characters,getContext());
        //We voegen een clickListener toe aan de adapter
        adapter.setCardClickedListener((CharacterAdapter.CardClickedListener) getActivity());
        //We voegen de adapter toe aan de recyclerView
        characterList.setAdapter(adapter);

        return layout;
    }
}
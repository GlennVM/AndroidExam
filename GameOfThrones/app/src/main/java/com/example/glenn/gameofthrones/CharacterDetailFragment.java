package com.example.glenn.gameofthrones;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.glenn.gameofthrones.models.GOTCharacter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 10/08/2016.
 */
public class CharacterDetailFragment extends Fragment {

    //Butterknife bind de ImageView uit de xml aan de lokale variabele
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.description)
    TextView description;
    //  @Bind(R.id.name)
    //TextView name;

    private final String RESOURCE_DRAWABLE = "drawable";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //We maken een nieuwe LinearLayout aan en steken hier de juist view in
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_character_detail,container,false);
        ButterKnife.bind(this,layout);

        //We halen de doorgegeven argumenten op
        Bundle args = getArguments();

        //We controlleren of de argumenten een charachter bevatten
        if(args.containsKey("character")){
            //Als dit het geval is halen we het character op uit de args
            //doordat dit parcelable is, kunnen we hier gewoon een GOTCharacter van maken
            GOTCharacter character = args.getParcelable("character");
            //name.setText(character.getName());
            //We stellen de omschrijving van het character in
            description.setText(character.getDescription());
            //We halen de naam van de foto op
            String photoname = character.getName().toLowerCase().replaceAll("\\s","");
            Log.i("photoname",photoname);
            //We voegen de foto toe aan het scherm
            image.setImageResource(getResources().getIdentifier(photoname,RESOURCE_DRAWABLE, getContext().getApplicationContext().getPackageName()));
        }
        return layout;
    }
}

